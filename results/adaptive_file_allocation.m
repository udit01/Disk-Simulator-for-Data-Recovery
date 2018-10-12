close all;
clear all;

f1 = fopen('D:/Data Recovery/New Performance/Test9/output6.txt', 'rt');

%eps_op = zeros(68257, 1, 'double');
eps_model = zeros(0, 1, 'double');
%mem_util = zeros(68257, 1, 'double');
%action = zeros(z, 1, 'int16');
%lambda_op = zeros(68257, 1, 'int16');
lambda_model = zeros(0, 1, 'int16');
%sigma_op = zeros(68257, 1, 'int16');
sigma_model = zeros(0, 1, 'int16');
%rho_op = zeros(68257, 1, 'int16');
rho_model = zeros(0, 1, 'int16');
%mu_op = zeros(68257, 1, 'int16');
mu_model = zeros(0, 1, 'int16');
%P_op = zeros(68257, 1, 'double');
P_model = zeros(0, 1, 'double');
model_array = zeros(0, 1, 'int32');
%op_array = (1:1:68257);
prob = 0;
flag = 0;

while ~feof(f1)
    %op_it_no = int32(str2double(fgetl(f1)(22:end))));
    %disp(op_it_no);
    try
        temp = fgetl(f1);
        epsi = str2double(temp);
        disp(epsi);
        temp = fgetl(f1);
        model_it_no = int32(str2double(temp(23:end)));
        fgetl(f1);
        temp = fgetl(f1);
        perf = str2double(temp(14:end));
        temp = fgetl(f1);
        state = strsplit((temp(8:18)), ', ');
        lambda = state(1);
        sigma = state(2);
        rho = state(3);
        mu = state(4);
        disp(state);
        fgetl(f1);
    catch ME
        prob = prob + 1;
        while flag == 0
            temp = fgetl(f1);
            if ~isempty(temp) && temp(1) == 'M'
                fseek(f1, -1*length(temp), 'cof');
                flag = 1;
            end
        end
        flag = 0;
        continue;
    end
    model_array = [model_array; model_it_no];
    eps_model = [eps_model; epsi];
    lambda_model = [lambda_model; int16(str2double(lambda))];
    sigma_model = [sigma_model; int16(str2double(sigma))];
    rho_model = [rho_model; int16(str2double(rho))];
    mu_model = [mu_model; int16(str2double(mu))];
    P_model = [P_model; perf];
end

disp(sprintf('Data discrepencies: %d', prob));

% lambda_model = double(lambda_model);
% rho_model = double(rho_model);
% sigma_model = double(sigma_model);
% mu_model = double(mu_model);

% % Memory Utilization vs OIN
% figure(1)
% scatter(op_array, mem_util, 0.7)
% xlabel('Operation Iteration Number')
% ylabel('Memory Utilization (%)');
% 
% % Memory Utilization vs OIN (log)
% figure(2)
% scatter(op_array, mem_util, 0.7)
% set(gca, 'xscale', 'log')
% xlabel('Operation Iteration Number (log)')
% ylabel('Memory Utilization (%)');

% Performance vs MIN
figure
scatter(model_array(1:10:end), P_model(1:10:end), 3, 'filled')
hold on
fp = movmean(P_model(1:10:end), 10);
plot(model_array(1:10:end), fp, 'r')
legend('Performace', 'Moving Avg')
xlabel('Model Iteration Number')
ylabel('Performance');
hold off;

% Performance vs MIN (log)
figure
scatter(model_array(1:10:end), P_model(1:10:end), 0.7)
set(gca, 'xscale', 'log')
xlabel('Model Iteration Number')
ylabel('Performance (log)');

% % Performance vs OIN 
% figure(5)
% scatter(op_array, P_op, 0.7)
% xlabel('Operation Iteration Number')
% ylabel('Performance');
% 
% % Performance vs OIN (log)
% figure(6)
% scatter(op_array, P_op, 0.7)
% set(gca, 'xscale', 'log')
% xlabel('Operation Iteration Number (log)')
% ylabel('Performance');

% Epsilon vs MIN
figure
scatter(model_array(1:20:end), eps_model(1:20:end), 0.7)
xlabel('Model Iteration Number')
ylabel('Epsilon');

% Lambda vs MIN
figure
plot(model_array(1:10:end), lambda_model(1:10:end))
hold on
f1 = fit(model_array(1:5:end), lambda_model(1:5:end), 'exp2');
plot(f1, '--r')
legend('lambda data', 'fitted curve')
xlabel('Model Iteration Number')
ylabel('Lambda')
hold off;


% Rho vs MIN
figure
plot(model_array(1:10:end), rho_model(1:10:end))
hold on
f2 = fit(model_array(1:40:end), rho_model(1:40:end), 'exp2')
plot(f2, '--r')
legend('rho data', 'fitted curve')
xlabel('Model Iteration Number')
ylabel('Rho')
hold off;

% Sigma vs MIN
figure
plot(model_array(1:10:end), sigma_model(1:10:end))
hold on
f3 = fit(model_array(1:20:end), sigma_model(1:20:end), 'exp2')
plot(f3, '--r')
legend('sigma data', 'fitted curve')
xlabel('Model Iteration Number')
ylabel('Sigma')
hold off;

% Mu vs MIN
figure
hold on
plot(model_array(1:10:end), mu_model(1:10:end))
f4 = fit(model_array(1:71:end), mu_model(1:71:end), 'exp2')
plot(f4, '--r')
legend('mu data', 'fitted curve', 'Location', 'southeast')
xlabel('Model Iteration Number')
ylabel('Mu')
hold off;




    