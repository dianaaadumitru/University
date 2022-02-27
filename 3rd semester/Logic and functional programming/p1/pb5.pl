%Write a predicate to compute the union of two sets.

removeOcc([],_,[]).
removeOcc([H|T],E,R):-H=:=E,
    removeOcc(T,E,R).
removeOcc([H|T],E,[H|R]):-H=\=E,
    removeOcc(T,E,R).


unionn([], [], []).
unionn([H|T], B, [H|R]) :-
    removeOcc([H|T], H, R1),
    removeOcc(B, H, R2),
    unionn(R1, R2, R).


%Write a predicate to determine the set of all the pairs of elements in a list

search([H|_],EL):-
    H=:=EL,!.
search([_|T],EL):-
    search(T,EL).

setPairs([],[_|_],[]).
setPairs([H|T],AUX,R):-
    H = [A,B|_],
    search(A,AUX),
    !,
    R1 is [A|R],
    AUX1 is [A|AUX],
    search(B,AUX),
    !,
    R2 is [B|R1],
    AUX2 is [B|AUX1],
    setPairs(T,AUX2,R2).
setPairs([H|T],[H|AUX],R):-
    setPairs(T,AUX,R).
