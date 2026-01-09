insert into vehicles (
   id,
   brand,
   model,
   price,
   stock,
   deleted
) values
-- VEHÍCULOS ACTIVOS QUE CUMPLEN LA CONDICIÓN (price > 20000 AND stock < 10)
 ( 1,
           'Toyota',
           'Corolla-X1',
           25000,
           5,
           'N' ),( 2,
                   'Honda',
                   'Civic-Z2',
                   30000,
                   3,
                   'N' ),( 3,
                           'Mazda',
                           'Mazda3-A',
                           28000,
                           7,
                           'N' ),( 4,
                                   'Ford',
                                   'Focus-R',
                                   22000,
                                   2,
                                   'N' ),( 5,
                                           'Nissan',
                                           'Sentra-P',
                                           26000,
                                           4,
                                           'N' ),( 6,
                                                   'Hyundai',
                                                   'Elantra-G',
                                                   27000,
                                                   6,
                                                   'N' ),( 7,
                                                           'Kia',
                                                           'Cerato-L',
                                                           24000,
                                                           8,
                                                           'N' ),( 8,
                                                                   'Chevrolet',
                                                                   'Cruze-T',
                                                                   29000,
                                                                   1,
                                                                   'N' ),( 9,
                                                                           'Volkswagen',
                                                                           'Jetta-Q',
                                                                           31000,
                                                                           9,
                                                                           'N' ),( 10,
                                                                                   'Subaru',
                                                                                   'Impreza-W',
                                                                                   33000,
                                                                                   2,
                                                                                   'N' ),

-- VEHÍCULOS ACTIVOS QUE NO CUMPLEN LA CONDICIÓN
                                                                                   ( 11,
                                                                                           'Toyota',
                                                                                           'Yaris-A',
                                                                                           18000,
                                                                                           15,
                                                                                           'N' ), -- price bajo
                                                                                           ( 12,
                                                                                                   'Honda',
                                                                                                   'City-B',
                                                                                                   19000,
                                                                                                   20,
                                                                                                   'N' ),( 13,
                                                                                                           'Mazda',
                                                                                                           'Mazda2-C',
                                                                                                           17000,
                                                                                                           12,
                                                                                                           'N' ),( 14,
                                                                                                                   'Ford',
                                                                                                                   'Fiesta-D'
                                                                                                                   ,
                                                                                                                   16000,
                                                                                                                   30,
                                                                                                                   'N' ),( 15
                                                                                                                   ,
                                                                                                                        'Nissan'
                                                                                                                        ,
                                                                                                                        'Versa-E'
                                                                                                                        ,
                                                                                                                        15000
                                                                                                                        ,
                                                                                                                        25,
                                                                                                                        'N' )
                                                                                                                        ,( 16
                                                                                                                        ,
                                                                                                                            'Hyundai'
                                                                                                                            ,
                                                                                                                            'Accent-F'
                                                                                                                            ,
                                                                                                                            14000
                                                                                                                            ,
                                                                                                                            18
                                                                                                                            ,
                                                                                                                            'N'
                                                                                                                            )
                                                                                                                            ,
                                                                                                                            (
                                                                                                                            17
                                                                                                                            ,
                                                                                                                                'Kia'
                                                                                                                                ,
                                                                                                                                'Rio-G'
                                                                                                                                ,
                                                                                                                                13000
                                                                                                                                ,
                                                                                                                                22
                                                                                                                                ,
                                                                                                                                'N'
                                                                                                                                )
                                                                                                                                ,
                                                                                                                                (
                                                                                                                                18
                                                                                                                                ,
                                                                                                                                    'Chevrolet'
                                                                                                                                    ,
                                                                                                                                    'Spark-H'
                                                                                                                                    ,
                                                                                                                                    12000
                                                                                                                                    ,
                                                                                                                                    40
                                                                                                                                    ,
                                                                                                                                    'N'
                                                                                                                                    )
                                                                                                                                    ,
                                                                                                                                    (
                                                                                                                                    19
                                                                                                                                    ,
                                                                                                                                        'Volkswagen'
                                                                                                                                        ,
                                                                                                                                        'Polo-I'
                                                                                                                                        ,
                                                                                                                                        20000
                                                                                                                                        ,
                                                                                                                                        50
                                                                                                                                        ,
                                                                                                                                        'N'
                                                                                                                                        )
                                                                                                                                        , -- price = 20000 (no cumple >)
                                                                                                                                        
                                                                                                                                        (
                                                                                                                                        20
                                                                                                                                        ,
                                                                                                                                            'Subaru'
                                                                                                                                            ,
                                                                                                                                            'Justy-J'
                                                                                                                                            ,
                                                                                                                                            18000
                                                                                                                                            ,
                                                                                                                                            60
                                                                                                                                            ,
                                                                                                                                            'N'
                                                                                                                                            )
                                                                                                                                            ,

-- VEHÍCULOS ACTIVOS QUE PUEDEN CAMBIAR DE CONDICIÓN POR UPDATE STOCK
                                                                                                                                            
                                                                                                                                            (
                                                                                                                                            21
                                                                                                                                            ,
                                                                                                                                                'BMW'
                                                                                                                                                ,
                                                                                                                                                'Series1-K'
                                                                                                                                                ,
                                                                                                                                                45000
                                                                                                                                                ,
                                                                                                                                                9
                                                                                                                                                ,
                                                                                                                                                'N'
                                                                                                                                                )
                                                                                                                                                , -- cumple inicialmente
                                                                                                                                                
                                                                                                                                                (
                                                                                                                                                22
                                                                                                                                                ,
                                                                                                                                                    'Audi'
                                                                                                                                                    ,
                                                                                                                                                    'A3-L'
                                                                                                                                                    ,
                                                                                                                                                    42000
                                                                                                                                                    ,
                                                                                                                                                    12
                                                                                                                                                    ,
                                                                                                                                                    'N'
                                                                                                                                                    )
                                                                                                                                                    , -- stock alto
                                                                                                                                                    
                                                                                                                                                    (
                                                                                                                                                    23
                                                                                                                                                    ,
                                                                                                                                                        'Mercedes'
                                                                                                                                                        ,
                                                                                                                                                        'A200-M'
                                                                                                                                                        ,
                                                                                                                                                        48000
                                                                                                                                                        ,
                                                                                                                                                        20
                                                                                                                                                        ,
                                                                                                                                                        'N'
                                                                                                                                                        )
                                                                                                                                                        ,
                                                                                                                                                        (
                                                                                                                                                        24
                                                                                                                                                        ,
                                                                                                                                                            'Volvo'
                                                                                                                                                            ,
                                                                                                                                                            'S40-N'
                                                                                                                                                            ,
                                                                                                                                                            46000
                                                                                                                                                            ,
                                                                                                                                                            15
                                                                                                                                                            ,
                                                                                                                                                            'N'
                                                                                                                                                            )
                                                                                                                                                            ,

-- VEHÍCULOS ELIMINADOS LÓGICAMENTE
                                                                                                                                                            
                                                                                                                                                            (
                                                                                                                                                            25
                                                                                                                                                            ,
                                                                                                                                                                'Toyota'
                                                                                                                                                                ,
                                                                                                                                                                'OldCorolla-O'
                                                                                                                                                                ,
                                                                                                                                                                21000
                                                                                                                                                                ,
                                                                                                                                                                5
                                                                                                                                                                ,
                                                                                                                                                                'S'
                                                                                                                                                                )
                                                                                                                                                                ,
                                                                                                                                                                (
                                                                                                                                                                26
                                                                                                                                                                ,
                                                                                                                                                                    'Honda'
                                                                                                                                                                    ,
                                                                                                                                                                    'OldCivic-P'
                                                                                                                                                                    ,
                                                                                                                                                                    23000
                                                                                                                                                                    ,
                                                                                                                                                                    6
                                                                                                                                                                    ,
                                                                                                                                                                    'S'
                                                                                                                                                                    )
                                                                                                                                                                    ,
                                                                                                                                                                    (
                                                                                                                                                                    27
                                                                                                                                                                    ,
                                                                                                                                                                        'Mazda'
                                                                                                                                                                        ,
                                                                                                                                                                        'OldMazda-Q'
                                                                                                                                                                        ,
                                                                                                                                                                        24000
                                                                                                                                                                        ,
                                                                                                                                                                        4
                                                                                                                                                                        ,
                                                                                                                                                                        'S'
                                                                                                                                                                        )
                                                                                                                                                                        ,
                                                                                                                                                                        (
                                                                                                                                                                        28
                                                                                                                                                                        ,
                                                                                                                                                                            'Ford'
                                                                                                                                                                            ,
                                                                                                                                                                            'OldFocus-R'
                                                                                                                                                                            ,
                                                                                                                                                                            26000
                                                                                                                                                                            ,
                                                                                                                                                                            3
                                                                                                                                                                            ,
                                                                                                                                                                            'S'
                                                                                                                                                                            )
                                                                                                                                                                            ,
                                                                                                                                                                            (
                                                                                                                                                                            29
                                                                                                                                                                            ,
                                                                                                                                                                                'Nissan'
                                                                                                                                                                                ,
                                                                                                                                                                                'OldSentra-S'
                                                                                                                                                                                ,
                                                                                                                                                                                27000
                                                                                                                                                                                ,
                                                                                                                                                                                2
                                                                                                                                                                                ,
                                                                                                                                                                                'S'
                                                                                                                                                                                )
                                                                                                                                                                                ;