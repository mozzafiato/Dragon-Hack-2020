package si.dragonhack.globalwarn.DAOs;

public class User implements Comparable{
    private String username;
    private double activityTime;

    public User(String username, double activityTime){
        this.username=username;
        this.activityTime = activityTime;
    }

    public double getActivityTime() {
        return activityTime;
    }

    public String getUsername() {
        return username;
    }

    public void setActivityTime(double activityTime) {
        this.activityTime = activityTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(Object o) {
        User user=(User) o;
        int res=0;
        if (getActivityTime() > user.getActivityTime()) {res=-1;  }
        if (getActivityTime() < user.getActivityTime()){res=1;}
        return res;
    }
}
