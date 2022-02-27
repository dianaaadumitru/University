subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T],R):-
    subs(T,R).

summ([],0).
summ([H|T],R):-
    summ(T,R1),
    R is R1+H.

len([],0).
len([_|T],R):-
    len(T,R1),
    R is R1+1.

checkS(L):-
    summ(L, R),
    R mod 3 =:=0.

checkL(L,N):-
    len(L,R),
    N =< R.

onesol(L,N,R):-
    subs(L,R),
    checkS(R),
    checkL(R,N).

allsol(L,N,R):-
    findall(Ra, onesol(L,N,Ra),R).
