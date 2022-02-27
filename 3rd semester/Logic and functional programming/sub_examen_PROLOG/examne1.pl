insert(E,L,[E|L]).
insert(E,[H|T],[H|R]):-
    insert(E,T,R).

perm([],[]).
perm([H|T],R):-
    perm(T,RT),
    insert(H,RT,R).

check([],_).
check([_],_).
check([X,Y|T],N):-
    abs(X-Y) =< N,
    check([Y|T],N).

onesol(L,N,R):-
    perm(L,R),
    check(R,N).

allsol(L,N,R):-
    findall(Ra,onesol(L,N,Ra),R).

appendlist([], X, X).
appendlist([T|H], X, [T|L]) :- appendlist(H, X, L).



