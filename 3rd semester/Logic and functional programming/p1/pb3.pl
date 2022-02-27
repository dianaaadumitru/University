%Define a predicate to remove from a list all repetitive elements


nrOcc([],_,0).
nrOcc([H|T],E,N):- H=:=E,
    nrOcc(T,E,N1),
    N is N1+1.
nrOcc([H|T],E,N):- H=\=E,
    nrOcc(T,E,N).

removeOcc([],_,[]).
removeOcc([H|T],E,R):-H=:=E,
    removeOcc(T,E,R).
removeOcc([H|T],E,[H|R]):-H=\=E,
    removeOcc(T,E,R).

removeRep([],[]).
removeRep([H|T],R):-
    nrOcc([H|T],H,N),
    N > 1,
    removeOcc(T,H,R1),
    removeRep(R1,R).
removeRep([H|T],[H|R]):-
    nrOcc([H|T],H,N),
    N =< 1,
    removeRep(T,R).

%Remove all occurrence of a maximum value from a list on integer numbers


maxi(A,B,A):-A>=B.
maxi(A,B,B):-B>A.

maxList([],0).
maxList([H],H).
maxList([H|T],R):-
    maxList(T,RM),
    maxi(H,RM,R).

removeMax(L,R):-
    maxList(L,RM),
    removeOcc(L,RM,R).
