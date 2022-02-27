%a. At the 5% significance level, do the population variances seem to differ?

%test for 2 populations
alpha = 0.05;

%h0: sigma1  = sigma2
%h1: sigma1 =! sigma2 - two-tailed test

X = [46,37,39,48,47,44,35,31,44,37]
Y = [35,33,31,35,34,30,27,32,31,31]

[h, p, ci, stats] = vartest2(X, Y, 'alpha', alpha, 'tail', 'both')

if h == 0
    fprintf('H0 is not rejected, i.e, sigmas are equal\n');
else 
    fprintf('H0 is rejected, population variances differ \n');
end

%b. Find a 95% confidence interval for the difference of the average assembling times

n1 = length(X)
n2 = length(Y)

confLevel = 0.96
alpha = 1 - confLevel

s1 = var(X)
s2 = var(Y)

c = (s1/n1) / (s1/n1 + s2/n2)

inv_n = (c^2/(n1 - 1)) +((1 - c)^2 / (n2 - 1))
n = 1 / inv_n

xbar = mean(X)
ybar = mean(Y)

q = tinv(1 - alpha/2, n)

v1 = xbar - ybar - q * sqrt(s1/n1 + s2/n2)
v2 = xbar - ybar + q * sqrt(s1/n1 + s2/n2)

fprintf('Confidence interval for population mean, miu, where sigma is unknown is (%3.5f, %3.5f)\n', v1, v2)
