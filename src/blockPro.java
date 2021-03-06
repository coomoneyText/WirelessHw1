
import java.util.Random;
import java.util.Date;
class blockPro{
    private static Random random;
    public static void main(String[] args) {
        ResultPage r=new ResultPage();
        random =new Random();
        random.setSeed(new Date().getTime());
        int time_unit=100000;
        double delta=0.01;
        double count=0;//for <100000
        int []server={1,5,10};//for num of server
        int queue=0;//for num of queue
        int now_server=0;//for num custom in server
        int now_queue=0;//for num custom in queue
        double lambda=0;//for lambda
        double mu=0;//for mu
        int block_num=0;//for the num of custom be block
        int arrival_num=0;
        double []blockingProbability=new double[72];//for block probability
        int block_count=0;//for the count for block array
        int queue_flag=0;//for two table
        while(queue_flag<2){
            block_count=0;
            for(int i=0;i<3;++i){
                //queue=server or queue=0
                if(queue_flag==0)
                    queue=0;
                else
                    queue=server[i];
                for(lambda=0.01;lambda<=10;lambda*=10){
                    for(mu=0.01;mu<=10.24;mu*=4){
                        while (count<time_unit)
                        {
        
                            //for queue
                            boolean isArrive=possion(lambda,1,delta);
                            if(isArrive){
                                //cout<<now_server<<" ";
                                ++arrival_num;
                                if(now_server>=server[i]){
                                    //cout<<"full server!"<<endl;
                                    if(now_queue>=queue)
                                        ++block_num;
                                    else
                                        ++now_queue;
                                }
                                else{
                                    ++now_server;
                                }
                            }
                            boolean isDeparture=possion(mu,now_server,delta);
                            if(isDeparture){
                                if(now_server>0){
                                    //由queue補進來
                                    if(now_queue>0)
                                        --now_queue;
                                    //queue empty 有 server閒置
                                    else
                                        --now_server;
                                }
                            }
                            count+=delta;
                        }
        
                        
                        blockingProbability[block_count]=((double)block_num/(double)(arrival_num));
                        //System.out.println(block_count+" "+Double.toString(blockingProbability[block_count]));
                        //cout<<<<<<endl;
                        block_count++;
                        now_server=0;
                        now_queue=0;
                        block_num=0;
                        count=0;
                        arrival_num=0;
                        
                    }
                }
            }
        
            r.writeTable(blockingProbability,queue_flag);
            queue_flag++;

        }

    
        
    }
    public static boolean possion(double rate,int num,double delta){
        
        double inside=(-rate)*num*delta;
        double prob=1-Math.exp(inside);
        double random = Math.random();
        //cout<<random<<endl;
        boolean match=false;
        if(prob>random)
            match=true;
        else
            match=false;
        
        //cout<<prob<<" "<<random<<endl;
        return match;
    }
}