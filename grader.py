import sys
import subprocess

# -----------------------------
# Dependencia obligatoria
# -----------------------------
try:
    import requests
except ImportError:
    print("ERROR: La librería 'requests' no está instalada.")
    print("Ejecute:")
    print("    python3 -m pip install requests")
    sys.exit(1)

BASE = "http://localhost:8080/api/vehicles"
SCORE = 0

# Flags de validación
valid_get_all = False
valid_get_condition = False
delete_endpoint_ok = False
update_endpoint_ok = False


# -----------------------------
# Utilidades
# -----------------------------
def last_commit():
    print("Último commit:")
    try:
        r = subprocess.run(
            ["git", "log", "-1", "--format=%cd", "--date=iso"],
            capture_output=True, text=True
        )
        print(r.stdout.strip())
    except Exception:
        print("No disponible")
    print("-" * 50)


def safe_request(fn, label):
    try:
        return fn()
    except Exception as e:
        print(f"{label} ........ ERROR")
        print(f"Detalle: {e}")
        return None


# -----------------------------
# Requests
# -----------------------------
def get_all():
    r = requests.get(BASE)
    r.raise_for_status()
    return r.json()


def get_condition():
    r = requests.get(f"{BASE}/low-stock-expensive")
    r.raise_for_status()
    return r.json()


def delete_model(model):
    return requests.patch(f"{BASE}/delete/{model}")


def update_stock(id_, stock):
    return requests.patch(f"{BASE}/stock", json={
        "id": id_,
        "stock": stock
    })


# -----------------------------
# Ejecución
# -----------------------------
if __name__ == "__main__":
    last_commit()

    # -------------------------
    # GET /api/vehicles
    # -------------------------
    all_before = safe_request(get_all, "GET /api/vehicles")
    if isinstance(all_before, list):
        print("GET /api/vehicles ........ OK")
        SCORE += 2
        valid_get_all = True
    else:
        print("GET /api/vehicles ........ FAIL")

    # -------------------------
    # GET /low-stock-expensive
    # -------------------------
    condition_before = safe_request(get_condition, "GET /low-stock-expensive")
    if isinstance(condition_before, list):
        print("GET /low-stock-expensive . OK")
        SCORE += 2
        valid_get_condition = True
    else:
        print("GET /low-stock-expensive . FAIL")

    # -------------------------
    # PATCH delete/{model} (endpoint solo)
    # -------------------------
    model_to_delete = None
    if valid_get_all and all_before:
        model_to_delete = all_before[0]["model"]

    if model_to_delete:
        r = delete_model(model_to_delete)
        if r.status_code in (200, 409):
            print("PATCH delete/{model} .... OK (endpoint)")
            SCORE += 1
            delete_endpoint_ok = True
        else:
            print("PATCH delete/{model} .... FAIL (endpoint)")
    else:
        print("PATCH delete/{model} .... SKIPPED")

    # -------------------------
    # PATCH /stock (endpoint solo)
    # -------------------------
    target = None
    if valid_get_condition and condition_before:
        target = condition_before[0]

    if target:
        r = update_stock(target["id"], target["stock"] + 50)
        if r.status_code == 200:
            print("PATCH /stock ............ OK (endpoint)")
            SCORE += 1
            update_endpoint_ok = True
        else:
            print("PATCH /stock ............ FAIL (endpoint)")
    else:
        print("PATCH /stock ............ SKIPPED")

    # -------------------------
    # Validación de flujo completo (11 pasos)
    # -------------------------
    if valid_get_all and valid_get_condition and delete_endpoint_ok and update_endpoint_ok:
        try:
            # Validar eliminación real
            all_after = get_all()
            models_after = {v["model"] for v in all_after}

            if model_to_delete in models_after:
                raise Exception("Eliminación lógica no aplicada")

            # Validar impacto del update
            condition_after = get_condition()
            ids_after = {v["id"] for v in condition_after}

            if target["id"] in ids_after:
                raise Exception("UPDATE stock no afectó la condición")

            print("FLUJO COMPLETO (11 pasos) OK")
            SCORE += 2

        except Exception as e:
            print("FLUJO COMPLETO (11 pasos) FAIL")
            print(f"Detalle: {e}")
    else:
        print("FLUJO COMPLETO (11 pasos) SKIPPED")

    # -------------------------
    # Resultado final
    # -------------------------
    print("-" * 50)
    print(f"TOTAL SCORE: {SCORE} / 8")
