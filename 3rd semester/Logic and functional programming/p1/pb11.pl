% Write a predicate to substitute an element from a list with another element in the list.
%
% substitute(L:list,OLD:number,New:number,R:list)
%
% subtitute(L: initial list,OLD: replaced element,NEW: new
% element,R: resulted list)
%
% substitute(i,i,i,o)
%
% Model matematic:
% substitute(l1l2...ln, old, new):
%        [], n=0
%        new + substitute(l2l3...ln, old, new), old = l1
%        l1 + substitute(l2l3...ln, old, new), otherwise


substitute([],_,_,[]).
substitute([H|T],OLD,NEW,[NEW|R]):-
    H=:=OLD,
    substitute(T,OLD,NEW,R).
substitute([H|T],OLD,NEW,[H|R]):-
    H=\=OLD,
    substitute(T,OLD,NEW,R).

%substitute([1,4,5,8,2,4,0],8,100,R).
%substitute([1,2,3,4,5],12,34,R).
%substitute([],2,4,R).
%substitute([2],2,4,R).



%Write a predicate to create the sublist (lm, …, ln) from the list (l1,…, lk).
%
% sublist(L:list,POS:number,N:number,M:number,R:list)
%
% sublist(L:initial list, POS:number of elements in the new list, N:beginning of the subset, M:end of the subset, R:resulted list)
%
% sublist(i,i,i,i,o)
%
% Model matematic:
% sublist(l1l2...ln, pos, m, n):
%        [], number of elements of the list = 0
%        l1 + sublist(l2l3...ln, pos+1, m, n), m <= pos and    pos <= n
%       sublist(l2l3...ln, pos+1, m, n), otherwise


sublist([],_,_,_,[]).
sublist([H|T],POS,M,N,[H|R]):-
    POS>=M,
    POS=<N,
    POS1 is POS+1,
    sublist(T,POS1,M,N,R).
sublist([_|T],POS,M,N,R):-
    POS1 is POS+1,
    sublist(T,POS1,M,N,R).


% sublist([1,7,4,2,8,5,6,3,9,22],0,3,6,R).
% sublist([1,7,4,2,8,5,6,3,9,22],0,0,4,R).
% sublist([1,7,4,2,8,5,6,3,9,22],0,1,9,R).
% sublist([1,7,4,2,8,5,6,3,9,22],0,11,13,R).
% sublist([],0,3,6,R).





