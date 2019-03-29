## Exercício 4

### Gramática

Seja a gramática e suas ações semânticas abaixo:

(1)   Calc = Ea '=' {printf("%f", Ea.val);}                              <br>
(2)   Ea = Ta {Ear.vh = Ta.val;} Ear {Ea.val = Ear.vs;}                  <br>
(3)   Ear = '+' Ta {Ear1.vh = Ear.vh + Ta.val;} Ear {Ear.vs = Ear1.vs;}  <br>
(4)   Ear = '-' Ta {Ear1.vh = Ear.vh - Ta.val;} Ear {Ear.vs = Ear1.vs;}  <br>
(5)   Ear = $ {Ear.vs = Ear.vh;}                                         <br>
(6)   Ta = Fa {Tar.vh = Fa.val;} Tar {Ta.val = Tar.vs;}                  <br>
(7)   Tar = '*' Fa {Tar1.vh = Tar.vh * Fa.val;} Tar {Tar.vs = Tar1.vs;}  <br>
(8)   Tar = '/' Fa {Tar1.vh = Tar.vh / Fa.val;} Tar {Tar.vs = Tar1.vs;}  <br>
(9)   Tar = $	{Tar.vs = Tar.vh;}                                         <br>
(10)  Fa = '(' Ea ')' {Fa.val = Ea.val;}                                 <br>
(11)  Fa = 'cteN' {Fa.val = atof(cteN.lex);}                             <br>

As funções relativas as ações semânticas serão:

#### Calc
```c
void Calc() {
    if (tk.categ == atr) {
        tk.next();
        printf("%f", Ea());
    } else {
        printf("ERRO!");
    }
}
```

#### Ea
```c
float Ea() {
    return Ear(Ta());
}
```

#### Ear
```c
float Ear(float Earvh) {
    if (tk.categ == mais) {
        return Ear(Earvh + Ta());
    } else if (tk.categ == menos) {
        return Ear(Earvh - Ta());
    } else {
        return Earvh;
    }
}
```

#### Ta
```c
float Ta() {
    return Tar(Fa());
}
```

#### Tar
```c
float Tar(Tarvh) {
    if (tk.categ == mult) {
        return Tar(Tarvh * Fa());
    } else if (tk.categ == div) {
        return Tar(Tarvh / Fa());
    } else {
        return Tarvh;
    }
}
```

#### Fa
```c
float Fa() {
    if (tk.categ == par_abre) {
        tk.next();
        
        if (tk.categ == par_fecha) {
            tk.next();
            return Ea();
        } else {
            printf("ERRO!");
        }
    } else if (tk.categ == cten) {
        tk.next();
        return atof(tk.lex);
    } else {
        printf("ERRO!");
    }
}
```
