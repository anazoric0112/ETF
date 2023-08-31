clc, clear, close all

%%
data=readtable("D:\5. semestar\NM\Projekat\Rice.csv");

inp=[data.AREA,data.PERIMETER,data.MAJORAXIS,data.MINORAXIS, data.ECCENTRICITY,data.CONVEX_AREA,data.EXTENT];
outp=data.CLASS;

inp=inp';
outp=outp';

outp = categorical(outp);
outp = renamecats(outp,{'Cammeo','Osmancik','Kecimen'},{'1','2','3'});

figure, histogram(outp)

%% podela na trening, test, validaciju

k1=inp(:,outp=='1');
k2=inp(:,outp=='2');
k3=inp(:,outp=='3');

n1=length(k1);
k1tr=k1(:,1:0.7*n1);
k1ts=k1(:,0.7*n1+1:0.85*n1);
k1v=k1(:,0.85*n1+1:n1);

n2=length(k2);
k2tr=k2(:,1:0.7*n2);
k2ts=k2(:,0.7*n2+1:0.85*n2);
k2v=k2(:,0.85*n2+1:n2);

n3=length(k3);
k3tr=k3(:,1:0.7*n3);
k3ts=k3(:,0.7*n3+1:0.85*n3);
k3v=k3(:,0.85*n3+1:n3);


intr=[k1tr,k2tr,k3tr];
prvi=[ones(1,length(k1tr)),zeros(1,length(k2tr)),zeros(1,length(k3tr))];
drugi=[zeros(1,length(k1tr)),ones(1,length(k2tr)),zeros(1,length(k3tr))];
treci=[zeros(1,length(k1tr)),zeros(1,length(k2tr)),ones(1,length(k3tr))];
outtr=[prvi;drugi;treci];
rng(200)
ind = randperm(length(outtr));
intr = intr(:, ind);
outtr = outtr(:, ind);

ints=[k1ts,k2ts,k3ts];
prvi=[ones(1,length(k1ts)),zeros(1,length(k2ts)),zeros(1,length(k3ts))];
drugi=[zeros(1,length(k1ts)),ones(1,length(k2ts)),zeros(1,length(k3ts))];
treci=[zeros(1,length(k1ts)),zeros(1,length(k2ts)),ones(1,length(k3ts))];
outts=[prvi;drugi;treci];

inv=[k1v,k2v,k3v];
prvi=[ones(1,length(k1v)),zeros(1,length(k2v)),zeros(1,length(k3v))];
drugi=[zeros(1,length(k1v)),ones(1,length(k2v)),zeros(1,length(k3v))];
treci=[zeros(1,length(k1v)),zeros(1,length(k2v)),ones(1,length(k3v))];
outv=[prvi;drugi;treci];


inall=[intr,inv];
outall=[outtr,outv];


%%
arhs={[6,5,4],[8,9,10,7],[10,9,3,5,6],[12,15,8,4,3]}; 
lrs=[0.2,0.06,0.037,0.4];
regs=[0.25,0.45,0.7];
amax=0;
best_lr=0;
best_ep=0;
best_reg=0;
best_arh=[];

ii=0; im=0;
for lr=lrs
    for arh=1:length(arhs)
        for reg=regs
            
            ii=ii+1;
            rng(200)
            net=patternnet(arhs{arh});

            net.divideFcn='divideind';
            net.divideParam.trainInd=1:length(intr);
            net.divideParam.valInd=length(intr)+1:length(inall);
            net.divideParam.testInd=[];

            for i = 1 : length(arh)
                net.layers{i}.transferFcn = 'tansig';
            end
            net.layers{i+1}.transferFcn = 'softmax';

            net.trainFcn='trainrp';
            net.trainParam.lr=lr;
            net.performParam.regularization=reg;

            net.trainParam.epochs=1000;
            net.trainParam.min_grad=1e-5;
            net.trainParam.goal=1e-4;
            net.trainParam.max_fail=25;

            [net,info]=train(net,inall,outall);

            pred=sim(net,inv);
            [e,cm]=confusion(outv,pred);
            %a=sum(trace(cm))/sum(sum(cm))*100;
            a = 2*cm(2, 2)/(cm(2, 1)+cm(1, 2)+2*cm(2, 2));
            %disp(a); disp(lr);disp(reg);disp(ii);disp(arhs{arh});
            if a>amax
                best_lr=lr;
                best_arh=arhs{arh};
                best_reg=reg;
                best_ep=info.best_epoch;
                amax=a;
                im=ii;
            end
        end
    end
end
disp(best_lr);
disp(best_ep);
disp(best_reg);
disp(best_arh);
disp(amax);
disp(im);

%%
rng(200)
inall2=[inall,ints];
outall2=[outall,outts];
net=patternnet(best_arh);
net.divideFcn='divideind';
net.divideParam.trainInd=1:length(intr);
net.divideParam.valInd=length(intr)+1:length(inv)+length(intr)+1;
net.divideParam.testInd=length(inv)+length(intr)+2:length(inall2);

for i = 1 : length(best_arh)
                net.layers{i}.transferFcn = 'tansig';
end
net.layers{i+1}.transferFcn = 'softmax';

net.trainFcn='trainrp';
net.trainParam.lr=best_lr;
net.performParam.regularization=best_reg;

net.trainParam.epochs=best_ep;
net.trainParam.min_grad=1e-5;
net.trainParam.goal=1e-4;
net.trainParam.max_fail=25;

net=train(net,inall,outall);

%%
pred=sim(net,ints);
figure, plotconfusion(outts,pred)



