nrElements([], 0).
nrElements([_|T], R):-
    nrElements(T,R1),
    R is R1 + 1.

ratie([H1,H2|_],R):-
    R is H2-H1.

checkProgression([H1,H2],Rat, R):-
    R1 is H2-H1,
    R1 =:=  Rat,
    R is 1.
checkProgression([H1,H2],Rat, R):-
    R1 is H2-H1,
    R1 \=  Rat,
    R is 0.
checkProgression([H1,H2|_], Rat, R):-
    R1 is H2 - H1,
    R1 \= Rat,
    R is 0.
checkProgression([H1,H2|T], Rat, R):-
    R1 is H2 - H1,
    R1 =:= Rat,
    checkProgression([H2|T], Rat, R2),
    R is R2.

subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T], R):-
    subs(T,R).

oneSol(L,K,RL):-
    subs(L,RL),
    nrElements(RL, N),
    N =:= K,
    ratie(RL, Rez),
    checkProgression(RL,Rez,RP),
    RP =:= 1.


allSols(L,K,R):-
    findall(RL, oneSol(L,K,RL),R).
