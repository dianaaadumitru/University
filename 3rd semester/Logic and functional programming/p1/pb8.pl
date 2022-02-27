%Write a predicate to determine if a list has even numbers of elements without counting the elements from
%the list.


evenList([]).
evenList([_,_|T]):-evenList(T).

%Write a predicate to delete first occurrence of the minimum number from a list.

mini(A,B,A):- A=<B.
mini(A,B,B):- B<A.

miniList([],0).
miniList([H],H).
miniList([H|T],R):-
    miniList(T,RM),
    mini(H,RM,R).

removeFirstOcc([],_,_,[]).
removeFirstOcc([H|T],MINI,FLAG,[H|R]):-
    H=\=MINI,
    FLAG is 1,
    !,
    removeFirstOcc(T,MINI,1,R).
removeFirstOcc([H|T],MINI,FLAG,[H|R]):-
    H=\=MINI,
    FLAG is 0,
    !,
    removeFirstOcc(T,MINI,0,R).

removeFirstOcc([H|T],MINI,FLAG,[H|R]):-
    H=:=MINI,
    FLAG is 0,
    !,
    removeFirstOcc(T,MINI,0,R).
removeFirstOcc([H|T],MINI,FLAG,R):-
    H =:= MINI,
    FLAG is 1,
    !,
    removeFirstOcc(T,MINI,0,R).

removeFirst(A,R):-
    miniList(A,RMIN),
    removeFirstOcc(A,RMIN,1,R).


