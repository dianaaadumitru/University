append2([],R,[R]).
append2([H|R],R2,[H|T]):-
    append2(R,R2,T).

len([],0).
len([_|T],R):- len(T,R1), R is R1+1.


%with double values
merge1(A,[],A):-!.
merge1([],B,B):-!.
merge1([A|T1],[B|T2],[A|R]):-
    A=<B,
    !,
    merge1(T1,[B|T2],R).
merge1([A|T1],[B|T2],[B|R]):-
    A>B,
    !,
    merge1([A|T1],T2,R).

merge_sort([],[]).
merge_sort([A],[A]).
merge_sort(L,R):-
    splitt(L,Left,Right),
    merge_sort(Left,RL),
    merge_sort(Right,RR),
    merge1(RL,RR,R).

%without double values
merge2(A,[],A):-!.
merge2([],B,B):-!.
merge2([A|T1],[B|T2],[A|R]):-
    A=:=B,
    !,
    merge2(T1,T2,R).
merge2([A|T1],[B|T2],[A|R]):-
    A=<B,
    !,
    merge2(T1,[B|T2],R).
merge2([A|T1],[B|T2],[B|R]):-
    A>B,
    !,
    merge2([A|T1],T2,R).


mergeSort([],[]).
mergeSort([A],[A]).
mergeSort(L,R):-
    splitt(L,Left,Right),
    mergeSort(Left,RL),
    mergeSort(Right,RR),
    merge2(RL,RR,R).

split(L, LC, LC, L) :-
    len(L, RL),
    len(LC, RLC),
    Diff is RLC - RL,
    abs(Diff, AUX),
    AUX =< 1.
split([H|T], LC, Left, Right) :-
    append2(LC, H, RA),
    split(T, RA, Left, Right).

splitt(L, Left, Right):- split(L,[], Left, Right).

