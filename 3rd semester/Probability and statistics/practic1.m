pkg load statistics


%a. At the 5% significance level, do the population variances seem to differ?

%test for 2 populations
%alpha = input('significance level (0,1) = ');
alpha = 0.05;

%h0: sigma1  = sigma2
%h1: sigma1 =! sigma2 - two-tailed test

X1 = [4.6 0.7 4.2 1.9 4.8 6.1 4.7 5.5 5.4]; %sample test 1

X2 = [2.5 1.3 2.0 1.8 2.7 3.2 3.0 3.5 3.4]; %sample test 2

#N1 = length(X1)
#N2 = length(X2)

%tail values: -1 for left tailed
%           : 0 for both, default
%           : 1 for rigth tailed
tail = 0;
[h, p, ci, stats] = vartest2(X1,X2,'alpha',alpha,'tail','both');
%p-P value; ci = confidence level

if h == 0
    fprintf('H0 is not rejected, i.e, sigmas are equal\n');
else 
    fprintf('H0 is rejected, population variances differ \n');
end


% b. At the same significance level, does it seem that on avarage, steel pipes  lose more heat than glass pipes?
q1 = finv(alpha/2, stats.df1, stats.df2);
q2 = finv(1-alpha/2, stats.df2, stats.df1);
fprintf('Observed value is %1.4f\n', stats.fstat);
fprintf('P-value is %1.4f\n', p);
fprintf('Rejection region R is (-inf, %3.4f) U (%3.4f, inf)\n', q1, q2);

fprintf('\n\npoint b !\n');