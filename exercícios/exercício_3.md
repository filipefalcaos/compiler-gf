## Exercício 3

### Gramática
Seja a gramática abaixo:

(1)  Res = Ea '='     <br>
(2)  Ea = Ea '+' Ta   <br>
(3)  Ea = Ea '-' Ta   <br>
(4)  Ea = Ta          <br>
(5)  Ta = Ta '*' Fa   <br>
(6)  Ta = Ta '/' Fa   <br>
(7)  Ta = Fa          <br>
(8)  Fa = '(' Ea ')'  <br>
(9)  Fa = 'cteN'      <br>

Removendo as recursões a esquerda em Ea e Ta, temos:

(1)   Res = Ea '='      <br>
**(2) Ea = Ta Ear       <br>
(3)   Ear = '+' Ta Ear  <br>
(4)   Ear = '-' Ta Ear  <br>
(5)   Ear = $           <br>
(6)   Ta = Fa Tar       <br>
(7)   Tar = '*' Fa Tar  <br>
(8)   Tar = '/' Fa Tar  <br>
(9)   Tar = $**         <br>
(10)  Fa = '(' Ea ')'   <br>
(11)  Fa = 'cteN'       <br>

Logo, as ações semânticas serão:

(1)   Res = Ea '=' **{printf("%f", Ea.val);}**                                   <br>
(2)   Ea = Ta **{Ear.vh = Ta.val;}** Ear **{Ea.val = Ear.vs;}**                  <br>
(3)   Ear = '+' Ta **{Ear1.vh = Ear.vh + Ta.val;}** Ear **{Ear.vs = Ear1.vs;}**  <br>
(4)   Ear = '-' Ta **{Ear1.vh = Ear.vh - Ta.val;}** Ear **{Ear.vs = Ear1.vs;}**  <br>
(5)   Ear = $ **{Ear.vs = Ear.vh;}**                                             <br>
(6)   Ta = Fa **{Tar.vh = Fa.val;}** Tar **{Ta.val = Tar.vs;}**                  <br>
(7)   Tar = '*' Fa **{Tar1.vh = Tar.vh * Fa.val;}** Tar **{Tar.vs = Tar1.vs;}**  <br>
(8)   Tar = '/' Fa **{Tar1.vh = Tar.vh / Fa.val;}** Tar **{Tar.vs = Tar1.vs;}**  <br>
(9)   Tar = $	**{Tar.vs = Tar.vh;}**                                             <br>
(10)  Fa = '(' Ea ')' **{Fa.val = Ea.val;}**                                     <br>
(11)  Fa = 'cteN' **{Fa.val = atof(cteN.lex);}**                                 <br>
