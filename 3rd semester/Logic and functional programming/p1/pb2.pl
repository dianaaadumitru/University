%Write a predicate to remove all occurrences of a certain atom from a list

removeOcc([],_,[]).
removeOcc([H|T],N,R):- H=:=N,
    removeOcc(T,N,R).
removeOcc([H|T],N,[H|R]):- H=\=N,
    removeOcc(T,N,R).


% Define a predicate to produce a list of pairs (atom n) from an initial list of atoms. In this initial list atom has
%n occurrences

nrOcc([],_,0).
nrOcc([H|T],E,N):- H=:=E,
    nrOcc(T,E,N1),
    N is N1+1.
nrOcc([H|T],E,N):- H=\=E,
    nrOcc(T,E,N).

numberatom([],[]).
numberatom([H|T],[[H,RC]|R]):-
    nrOcc([H|T],H,RC),
    removeOcc(T,H,R1),
    numberatom(R1,R).

