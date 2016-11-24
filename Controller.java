public class Controller {
    Model m;
    View app;
    Controller c;
    int range1, range2;

    Controller(){
        this.m = new Model();
        this.app = new View();
        //firstLoad(c);
    }
    public void firstLoad(Controller c){
        Thread t1 = new Thread();
        t1.start();
        m.makeFirstTable();
        this.c = c;
        app.setController(c);
        app.pack(); //Эта команда подбирает оптимальный размер в зависимости от содержимого окна
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }

    public String[][] showTables(){
        return m.getSelectAll();
    }

    public String[][] showQuery1(){
        return m.getNamesSurnamesSorted();
    }

    public String[][] showQuery2(){
        return m.getNamesSurnamesCreditInterval(range1, range2);
    }

    public void modelExecuteStatement(String s){
        m.queryMake(s);
    }

    public void setRanges(int r1, int r2){
        this.range1 = r1;
        this.range2 = r2;
    }

    public Model getModel(){
        return m;
    }

    public View getView(){
        return app;
    }

    public Controller getController(){
        return c;
    }

    public static void main(String []argv){
        final Controller c =  new Controller();
        c.firstLoad(c);
    }

}

