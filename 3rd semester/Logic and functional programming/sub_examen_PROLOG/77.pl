nrElements([], 0).
nrElements([_|T], R):-
    nrElements(T,R1),
    R is R1 + 1.

sum([], 0).
sum([H|T], S):-
    sum(T,S1),
    S is S1 + H.

insert(E, L, [E|L]).
insert(E, [H|T], [H|R]):-
    insert(E, T, R).

arr([], []).
arr([_|T], R):-
    arr(T, R).
arr([H|T], R1):-
    arr(T, R),
    insert(H, R, R1).

oneSol(L, RL):-
    arr(L, RL),
    sum(RL, S),
	S mod 2 =:= 1,
    nrElements(RL, N),
    N mod 2 =:= 0.

allSols(L, R):-
    findall(RL, oneSol(L, RL), R).
