%a. At the 5% significance level, do the population variances seem to differ?

%test for 2 populations
alpha = 0.05;

%h0: sigma1  = sigma2
%h1: sigma1 =! sigma2 - two-tailed test

X = [1021,980,1017,988,1005,998,1014,985,995,10024,1030,1015,995,1023];
Y = [1070,970,993,1013,1006,1002,1014,997,1002,1010,975];

[h, p, ci, stats] = vartest2(X, Y, 'alpha', alpha, 'tail', 'both')

if h == 0
    fprintf('H0 is not rejected, i.e, sigmas are equal\n');
else 
    fprintf('H0 is rejected, population variances differ \n');
end

%b. At the same significance level, on average, does Supplier A seem to be more reliable?

q1 = finv(alpha/2, stats.df1, stats.df2);
q2 = finv(1-alpha/2, stats.df2, stats.df1);
fprintf('Observed value is %1.4f\n', stats.fstat);
fprintf('P-value is %1.4f\n', p);
fprintf('Rejection region R is (-inf, %3.4f) U (%3.4f, inf)\n', q1, q2);