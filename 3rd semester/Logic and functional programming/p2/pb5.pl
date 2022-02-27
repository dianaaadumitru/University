%Substitute all occurrences of an element of a list with all the elements of another list.
%Eg. subst([1,2,1,3,1,4],1,[10,11],X) produces
%X=[10,11,2,10,11,3,10,11,4]

appendList([],L,L).
appendList([H|T],L,[H|R]):-
    appendList(T,L,R).

subst([],_,_,[]).
subst([H|T],E,L,R):-
    H=:=E,
    appendList(L,T,R1),
    subst(R1,E,L,R).
subst([H|T],E,L,[H|R]):-
    H=\=E,
    subst(T,E,L,R).


%For a heterogeneous list, formed from integer numbers and list of numbers, replace in every sublist all
%occurrences of the first element from sublist it a new given list.
% Eg.: [1, [4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1], 7] si [11, 11] =>
% [1, [11, 11, 1, 11, 11], 3, 6, [11, 11, 10, 1, 3, 9], 5, [11 11 11 11
%11 11], 7]


heterList([], _, []).
heterList([[H|HT]|T], E, [HR|R]) :-
    subst([H|HT], H, E, HR),
    heterList(T, E, R).
heterList([H|T], E, [H|R]) :- number(H),
    heterList(T, E, R).
