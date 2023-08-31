clc, clear, close all

%%
data = load('dataset3.mat');
data = cell2mat(struct2cell(data));

input = data(:,1:2);
output = data(:,3);
k1 = input(output ==1,:);
k2 = input(output==2,:);
k3 = input(output ==3,:);
outoh=zeros(length(output),3);
outoh(output==1,1)=1;
outoh(output==2,2)=1;
outoh(output==3,3)=1;

figure, hold all 
plot(k1(:,1), k1(:,2),'*');
plot(k2(:,1), k2(:,2),'o');
plot(k3(:,1), k3(:,2),'*');

%%
input=input';
outoh=outoh';
rng(200)
n=length(output);
ind=randperm(n);
indtr=ind(1:0.8*n);
indts=ind(0.8*n:n);
inputtrain = input(:,indtr);
inputtest = input(:,indts);

outtrain = outoh(:,indtr);
outtest = outoh(:,indts);

%%
layers = [4,4,4,4]; %4 4 4 4
net = patternnet(layers);
net.trainParam.epochs = 800;
net.trainParam.min_grad = 1e-5;
net.trainParam.goal = 1e-4;
net.divideFcn ='';
net.performParam.regularization = 0;


for i=1:length(layers)
    net.layers{i}.transferFcn='tansig';
end
net.layers{i+1}.transferFcn='softmax';


%%
rng(200)
net = train(net,inputtrain,outtrain);

%%
predict = sim(net,inputtest);
predicttrain=sim(net,inputtrain);

figure,plotconfusion(outtest,predict);
figure, plotconfusion(outtrain,predicttrain);

%%
l=500;
inputgo=[];
x1=linspace(-5,5,l);
x2=linspace(-5,5,l);

for x11=x1
    pom=[x11*ones(1,l);x2];
    inputgo=[inputgo,pom];
end

predgo=sim(net,inputgo);
[vr,klasa]=max(predgo);

k1go=inputgo(:,klasa==1);
k2go=inputgo(:,klasa==2);
k3go=inputgo(:,klasa==3);

figure, hold all
plot(k1go(1,:),k1go(2,:),'.');
plot(k2go(1,:),k2go(2,:),'.');
plot(k3go(1,:),k3go(2,:),'.');

plot(k1(:,1),k1(:,2),'bo');
plot(k2(:,1),k2(:,2),'r*');
plot(k3(:,1),k3(:,2),'yd');
legend({'klasa 1','klasa 2','klasa 3'})
xlabel('naziv')
ylabel('vr')