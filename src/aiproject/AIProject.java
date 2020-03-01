package aiproject;
//Jealous Husband Problem

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/* State can be represented as 7 integer variables each having value PRESENT or ABSENT*/

class State implements Comparable<State>{
    final int h1,h2,h3,w1,w2,w3,b,hx;
    final State prev;
    final private static int PRESENT=1,ABSENT=0;
    public State(State prev,int h1, int h2, int h3, int w1, int w2, int w3, int b) {
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.b = b;
        this.prev = prev;
        hx=h1+h2+h3+w1+w2+w3;
      //  System.out.println("hx= "+hx);
        
    }
     boolean isValidState(){
        if(w1==PRESENT){//wife present 
            if(h1==ABSENT && (h2==PRESENT || h3==PRESENT)){//husband not present and either of other husband present
              //  System.out.println(this+" is not valid");
                return false;
            }
        }
        if(w2==PRESENT){//wife present
            if(h2==ABSENT && (h1==PRESENT || h3==PRESENT)){//husband not present and either of other husband present
            //  System.out.println(this+" is not valid");
                return false;
            }
        }
        if(w3==PRESENT){//wife present
            if(h3==ABSENT && (h1==PRESENT || h2==PRESENT)){//husband not present and either of other husband present
                // System.out.println(this+" is not valid");
                return false;
            }
        }
         // Checking right bank
        if(w1==ABSENT){//wife present 
            if(h1==PRESENT && (h2==ABSENT || h3==ABSENT)){//husband not present and either of other husband present
               // System.out.println(this+" is not valid");
                return false;
            }
        }
        if(w2==ABSENT){//wife present
            if(h2==PRESENT && (h1==ABSENT || h3==ABSENT)){//husband not present and either of other husband present
              //System.out.println(this+" is not valid");
                return false;
            }
        }
        if(w3==ABSENT){//wife present
            if(h3==PRESENT && (h1==ABSENT || h2==ABSENT)){//husband not present and either of other husband present
                // System.out.println(this+" is not valid");
                return false;
            }
        }
        // System.out.println(this+" is valid");
        return true;//all other cases
        
    }
    State generateNextState(int operatorCode){
        State s;
        switch(operatorCode){
            case 1:
                s=husbandWife(1);
                break;
            case 2:
                s=husbandWife(2);
                break;
            case 3:
                s=husbandWife(3);
                break;
            case 4:
                s=husbands(1,2);
                break;
            case 5:
                s=husbands(2,3);
                break;
            case 6:
                s=husbands(1,3);
                break;
            case 7:
                s=wives(1,2);
                break;
            case 8:
                s=wives(2,3);
                break;
            case 9:
                s=wives(1,3);
                break;
            case 10:
                s=husband(1);
                break;
            case 11:
                s=husband(2);
                break;
            case 12:
                s=husband(3);
                break;
            case 13:
                s=wife(1);
                break;
            case 14:
                s=wife(2);
                break;
            case 15:
                s=wife(3);
                break;
            default:
                s=null;
        }
        return s;
    }
    //operator 1,2,3
    //Couple number "code" travels from one bank to another
    private State husbandWife(int code){
        State s;
        /*variable code represents the id of husband and wife switching the shore*/
        
        switch (code){
            case 1:
                if((b==PRESENT &&h1==PRESENT && w1==PRESENT) || (b==ABSENT && h1==ABSENT && w1==ABSENT))
                    //operation valid only if the couple and boat is present and vice versa
                    s=new State(this,toggle(h1),h2,h3,toggle(w1),w2,w3,toggle(b));
                else
                    s=null;
                break;
            case 2:
                if((b==PRESENT && h2==PRESENT && w2==PRESENT) || (b==ABSENT&&h2==ABSENT && w2==ABSENT))
                    //operation valid only if the couple and boat is present and vice versa
                    s=new State(this,h1,toggle(h2),h3,w1,toggle(w2),w3,toggle(b));
                else
                    s=null;
                break;
                
            case 3:
                if((b==PRESENT && h3==PRESENT && w3==PRESENT) || (b==ABSENT&&h3==ABSENT && w3==ABSENT))
                    //operation valid only if the couple and boat is present and vice versa
                    s=new State(this,h1,h2,toggle(h3),w1,w2,toggle(w3),toggle(b));
                else
                    s=null;
                break;
            default:
                s=null;
        }
        return s;
    }
    //operation 4,5.6
    // Husband number code1 and code2 travel to another bank
    private State husbands(int code1,int code2){
        State s;
        if(code1==1 && code2==2){
            if((b==PRESENT &&h1==PRESENT && h2==PRESENT) || (b==ABSENT&&h1==ABSENT && h2==ABSENT))
              s=new State(this,toggle(h1),toggle(h2),h3,w1,w2,w3,toggle(b));
            else
              s=null;
        }
        else if(code1==2 && code2==3){
            if((b==PRESENT &&h2==PRESENT && h3==PRESENT) || (b==ABSENT&&h3==ABSENT && h2==ABSENT))
              s=new State(this,h1,toggle(h2),toggle(h3),w1,w2,w3,toggle(b));
            else
              s=null;
        }
        else{
            if((b==PRESENT &&h3==PRESENT && h1==PRESENT) || (b==ABSENT&&h3==ABSENT && h1==ABSENT))
              s=new State(this,toggle(h1),h2,toggle(h3),w1,w2,w3,toggle(b));
            else
              s=null;
        }
        return s;
    }
    //opertion 7,8,9
    //Wife number code1 and code2 travel to another bank
    private State wives(int code1,int code2){
        State s;
        if(code1==1 && code2==2){
            if((b==PRESENT &&w1==PRESENT && w2==PRESENT) || (b==ABSENT&&w1==ABSENT && w2==ABSENT))
              s=new State(this,h1,h2,h3,toggle(w1),toggle(w2),w3,toggle(b));
            else
              s=null;
        }
        else if(code1==2 && code2==3){
            if((b==PRESENT &&w2==PRESENT && w3==PRESENT) || (b==ABSENT&&w3==ABSENT && w2==ABSENT))
              s=new State(this,h1,h2,h3,w1,toggle(w2),toggle(w3),toggle(b));
            else
              s=null;
        }
        else{
            if((b==PRESENT &&w3==PRESENT && w1==PRESENT) || (b==ABSENT&&w3==ABSENT && w1==ABSENT))
              s=new State(this,h1,h2,h3,toggle(w1),w2,toggle(w3),toggle(b));
            else
              s=null;
        }
        return s;
    }
    //10,11,12
    // husband number code1 travel to another bank
    private State husband(int code1){
        State s=null;
        switch (code1) {
            case 1:
                if((h1==PRESENT && b==PRESENT) ||(h1==ABSENT && b==ABSENT))
                    s=new State(this,toggle(h1),h2,h3,w1,w2,w3,toggle(b));
                break;
            case 2:
                if((h2==PRESENT && b==PRESENT) ||(h2==ABSENT && b==ABSENT))
                 s=new State(this,h1,toggle(h2),h3,w1,w2,w3,toggle(b));
                break;
            case 3:
                if((h3==PRESENT && b==PRESENT) ||(h3==ABSENT && b==ABSENT))
                   s=new State(this,h1,h2,toggle(h3),w1,w2,w3,toggle(b));
                break;
            
        }
        return s;
    }
    //13,14,15
    // wife number code1 travel to another bank
    private State wife(int code1){
        State s=null;
        switch (code1) {
            case 1:
                if((w1==PRESENT && b==PRESENT) ||(w1==ABSENT && b==ABSENT))
                 s=new State(this,h1,h2,h3,toggle(w1),w2,w3,toggle(b));
                break;
            case 2:
                if((w2==PRESENT && b==PRESENT) ||(w2==ABSENT && b==ABSENT))
                    s=new State(this,h1,h2,h3,w1,toggle(w2),w3,toggle(b));
                break;
            case 3:
                if((w3==PRESENT && b==PRESENT) ||(w3==ABSENT && b==ABSENT))
                    s=new State(this,h1,h2,h3,w1,w2,toggle(w3),toggle(b));
                break;
            
        }
        return s;
    }
    // toggles the state of object
    private int toggle(int ob){
        return (ob==PRESENT)?ABSENT:PRESENT;
    }

    @Override
    public String toString() {
        return "State{" + "h1=" + h1 + ", h2=" + h2 + ", h3=" + h3 + ", w1=" + w1 + ", w2=" + w2 + ", w3=" + w3 + ", b=" + b + ", hx=" + hx + '}';
        
    }
    public void display(){
        if(prev!=null)
           prev.display();
        String s=""+((h1==1)?"h1":"")+((h2==1)?"h2":"")+((h3==1)?"h3":"")+((w1==1)?"w1":"")+((w2==1)?"w2":"")+((w3==1)?"w3":"")+((b==1)?"b":"")+"\t\t\t\t";
        s+=((h1==0)?"h1":"")+((h2==0)?"h2":"")+((h3==0)?"h3":"")+((w1==0)?"w1":"")+((w2==0)?"w2":"")+((w3==0)?"w3":"")+((b==0)?"b":"")+"\n";
        System.out.println(s);

    }   
   
    public boolean equals(State s){
        return h1==s.h1 && h2==s.h2 && h3==s.h3 && w1==s.w1 && w2==s.w2 && w3==s.w3 && b==s.b;
    }
    @Override
    public int compareTo(State t) {
        return hx-t.hx;
    }
    
}
public class AIProject {
    public static void main(String[] args) {
        State finalState=new State(null,1,1,1,1,1,1,1);
        State initialState=new State(null,1,0,0,1,0,0,0);
        //System.out.println(finalState);
        
        State s =bestFirstSearch(initialState,finalState);
        if(s!=null)
         s.display();
    }
    
    static State bestFirstSearch(State initialState,State finalState){
        if(!initialState.isValidState() || !finalState.isValidState()){
            System.out.println("Invalid input");
            return null;
        }
       PriorityQueue<State> frontier=new PriorityQueue<>();
       ArrayList<State> exploredSet=new ArrayList<>();
       frontier.add(initialState);
       while(true){
           if(frontier.isEmpty()){
               System.out.println("Frontier Empty");
               return null;
           }
           State currentState=frontier.poll();
         //  System.out.println("Current State = "+currentState);
           if(currentState.compareTo(finalState)==0){
               System.out.println("Final State found");
               return currentState;
           }
           exploredSet.add(currentState);
          // System.out.println(currentState+" added to explored set");
           for(int i=1;i<=15;i++){
               State state=currentState.generateNextState(i);
               //System.out.println(i+" "+state);
               if(state==null){
                   //System.out.println(i+" "+null);
                   continue;
               }
               if(!state.isValidState()){
                   //System.out.println(i+" invalid state");
                   continue;
               }
               if(!findInFrontier(frontier,state) && !findInES(exploredSet,state)){
                   //System.out.println(i+" "+state+" added to frontier");
                   frontier.add(state);
               }
//               else
//                  System.out.println("Not added");
               //System.out.println(""+i);
           }
           //System.out.println("\n");
       }
    }
    static boolean findInES(ArrayList<State> arr,State element){
        for (State arr1 : arr) {
            if(arr1.equals(element)){
                //System.out.println(element+" exists in ES");
                return true;
            }
        }
       // System.out.println(element+" does not exists in ES");
        return false;
    }
    static boolean findInFrontier(PriorityQueue<State> pq,State element){
        Iterator<State> itr = pq.iterator(); 
        while (itr.hasNext()){
            if(element.equals(itr.next())){
               // System.out.println(element+" exists in Frontier");
                return true;
            }
       }
       //System.out.println(element+"does not exists in Frontier");
       return false;
    }
}
