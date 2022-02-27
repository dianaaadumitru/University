subs([],[]).
subs([H|T],[H|R]):-
    subs(T,R).
subs([_|T],R):-
    subs(T,R).

lenList([],0).
lenList([_|T],L):-
    lenList(T,L1),
    L is L1 + 1.

increasing([]).
increasing([_]).
increasing([A,B|T]):-
    A < B,
    increasing([B|T]).

ins(E,L,[E|L]).
ins(E,[H|T],[H|R]):-
    ins(E,T,R).

arr([],[]).
arr([_|T],R):-
    arr(T,R).
arr([H|T],R1):-
    arr(T,R),
    ins(H,R,R1).

comb([],[]).
comb([_|T],R):-
    comb(T,R).
comb([H|T],[H|R]):-
    comb(T,R).

onestep(L,R):-
    arr(L,R),
    lenList(R,LEN),
    LEN >= 2,
    increasing(R).

allstep(L,R):-
    findall(RA, onestep(L,RA),R).
