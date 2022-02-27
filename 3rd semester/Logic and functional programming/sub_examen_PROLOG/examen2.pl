insert(E,L,[E|L]).
insert(E,[H|T],[H|R]):-
    insert(E,T,R).

produs([],1).
produs([H|T],R):-
    produs(T,R1),
    R is R1*H.

arr([E|_],1,[E]).
arr([_|T],K,R):-
    arr(T,K,R).
arr([H|T],K,R1):-
    K > 1,
    K1 is K-1,
    arr(T,K1,R),
    insert(H,R,R1).

onesol(L,K,P,R):-
    arr(L,K,R),
    produs(R,P).

allsol(L,K,P,R):-
    findall(Ra,onesol(L,K,P,Ra),R).
