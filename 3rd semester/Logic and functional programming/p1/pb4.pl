%Write a predicate to determine the difference of two sets

%are in A but not in B
diff([],_,[]).
diff([H|T],B,R):-
    search(B,H),
    diff(T,B,R).
diff([H|T],B,[H|R]):-
    diff(T,B,R).



search([V|_], V) :- !.
search([_|T], V) :- search(T, V).

%Write a predicate to add value 1 after every even element from a list.

addOne([],[]).
addOne([H|T],[H,1|R]):-
    H mod 2 =:= 0,
    addOne(T,R).
addOne([H|T],[H|R]):-
    H mod 2 =:= 1,
    addOne(T,R).

