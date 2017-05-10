package com.acrap.guitar_chord_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ChordView extends View {
    public enum Theme{
        Dark, Light
    }
    Paint p;
    public static Theme theme = Theme.Dark;
    String chord;
    int fontSize =10;
    int bigFontSize =20;
    int intervalX;
    int pointRadius;
    int topMerge;
    String chordName;
    int strWidth;
    int fretWidth;
    static int width = 100;
    static int height = 100;
    static int backgroundColor = Color.BLACK;
    static int pointColor = Color.WHITE;
    int intervalY;
    int leftMerge;
    int maxFret;
    int bottomMerge;

    static public void setSize(int size){
        width = size;
        height = size;
    }
    static public void refreshTheme(){
        if(theme == Theme.Light){
            backgroundColor = Color.WHITE;
            pointColor = Color.BLACK;
        }else{
            backgroundColor = Color.BLACK;
            pointColor = Color.WHITE;
        }
    }

    public ChordView(Context context){
        super(context);
        chord = "0 0 0 0 0 0";
        p = new Paint();
        chordName ="No";
        maxFret = 0;
    }

    public ChordView(Context context,String _chord,String _chordName){
        super(context);
        p = new Paint();
        chordName = _chordName;
        chord = _chord;
        maxFret = 0;
    }

    public void setChord(String _chordName, String tab){
        chord = tab;
        chordName = _chordName;
        invalidate();
    }
    private void drawPoint(Canvas canvas, int str,int fret){
        if(fret==0){
            return;
        }
        if(fret>maxFret)
            maxFret = fret;
        p.setColor(pointColor);
        int x = leftMerge;
        p.setStyle(Paint.Style.FILL);
        int y = topMerge + intervalY/2;
        for(int f = 1;f<fret;f++){
            y+=intervalY;
        }
        for(int i=1;i<str;i++){
            x+=intervalX;
        }
        int radius = ((intervalX+intervalY)/2)/4;
        canvas.drawCircle(x, y, radius, p);
    }
    public void setFontSize(int _fontSize){
        fontSize = _fontSize;
    }

    private void drawFretNumeration(Canvas canvas){
        int minFret = maxFret-2;
        p.setColor(pointColor);
        p.setTextSize(fontSize);
        int x = 5*intervalX + leftMerge +leftMerge/4;
        int y = this.getHeight()/6 + intervalY/2;
        if(minFret<=0){
            canvas.drawText(Integer.toString(1),x,y,p);
            return;
        }
        canvas.drawText(Integer.toString(minFret), x, y, p);
    }
    private  void drawBorder(Canvas c){
        p.setStrokeWidth(strWidth);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GREEN);
        c.drawRect(0,0,getWidth(),getHeight(),p);
    }
    private void drawNull(Canvas canvas, int str,boolean X){
        int x = leftMerge;
        int y = topMerge;
        p.setColor(pointColor);
        p.setTextSize(fontSize);
        for(int i=1;i<str;i++){
            x+=intervalX;
        }
        String text;
        if(X){
            text ="X";
        }else{
            text ="0";
        }
        canvas.drawText(text,x,y,p);
        drawFretNumeration(canvas);
    }

    void drawBare(Canvas canvas,int fret){
        int x = leftMerge;
        int w = ((intervalX+intervalY)/2)/3;;
        p.setColor(Color.WHITE);
        int x2 = getWidth()-leftMerge;
        p.setStyle(Paint.Style.FILL);
        int y = topMerge;
        for(int i=1;i<fret;i++)
            y+=intervalY;
        canvas.drawRect(x, y, x2, y + w, p);

    }

    int detectBare(int[] array){
        int fret = 0;
        int[] repeat = new int[3];
        int diff = maxFret - 3;
        for(int i=0;i<6;i++){
            if(array[i]>0){

            }
        }
        return fret;
    }

    private  void drawFret(Canvas canvas){
        int y = topMerge;
        p.setStrokeWidth(fretWidth);
        p.setColor(Color.GRAY);
        y += intervalY;
        canvas.drawLine(leftMerge,y,this.getWidth() - leftMerge,y,p);
        y += intervalY;
        canvas.drawLine(leftMerge,y,this.getWidth() - leftMerge,y,p);
    }
    private void drawStr(Canvas canvas){
        int x = leftMerge;
        int y = topMerge;
        p.setStrokeWidth(strWidth);
        p.setColor(getResources().getColor(R.color.red));
        canvas.drawLine(x, y, x, this.getHeight() - bottomMerge, p);
        x+=intervalX;

        p.setColor(getResources().getColor(R.color.orange));
        canvas.drawLine(x, y, x, this.getHeight()-bottomMerge, p);
        x+=intervalX;

        p.setColor(getResources().getColor(R.color.yellow));
        canvas.drawLine(x, y, x, this.getHeight()-bottomMerge, p);
        x+=intervalX;

        p.setColor(getResources().getColor(R.color.green));
        canvas.drawLine(x, y, x, this.getHeight()-bottomMerge, p);
        x+=intervalX;

        p.setColor(getResources().getColor(R.color.light_blue));
        canvas.drawLine(x, y, x, this.getHeight()-bottomMerge, p);
        x+=intervalX;
        p.setColor(getResources().getColor(R.color.blue));
        canvas.drawLine(x, y, x, this.getHeight()-bottomMerge, p);
    }
    private void drawChord(Canvas canvas){
        if(chord.length()<6)return;
        int[] strArray = new int[6];
        maxFret = 0;
        String [] tab = chord.split(" ");
        for(int i=0;i<6;i++){
            try {
                strArray[i] = Integer.parseInt(tab[i]);
                if(strArray[i]>maxFret)
                    maxFret= strArray[i];
            }catch (Exception e){
                strArray[i] = -1;
            }
        }
        if(maxFret>3){
            int diff = maxFret - 3;
            for(int i = 0;i<6;i++){
                if(strArray[i]>=0) {
                    drawPoint(canvas, i + 1, strArray[i] - diff);
                }else{
                    drawNull(canvas,i+1,true);
                }
            }
        }else{
            for(int i = 0;i<6;i++) {
                if(strArray[i]>=0) {
                    drawPoint(canvas, i + 1, strArray[i]);
                }else{
                    drawNull(canvas,i+1,true);
                }
            }
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(width,height);
    }
    protected void drawChordName(Canvas canvas){
        try {
            int x = getWidth() / 2 - bigFontSize / 2;
            p.setColor(pointColor);
            p.setStyle(Paint.Style.FILL);
            p.setTextSize(bigFontSize);
            canvas.drawText(chordName, x, getHeight() - bottomMerge / 4, p);
        }catch (Exception e){

        }
    }
    private void calculateSizes(){
        intervalX = this.getWidth()/6;
        intervalY = (this.getHeight()) / 5;
        topMerge = this.getHeight()/5;
        leftMerge = intervalX/2;
        fontSize = topMerge/2;
        bottomMerge = topMerge;
        pointRadius = ((intervalX+intervalY)/2)/4;
        bigFontSize = bottomMerge;
        fretWidth = intervalX/9;
        strWidth = intervalX/10;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        calculateSizes();
        //super.onDraw(canvas);
        canvas.drawColor(backgroundColor);
        drawFret(canvas);
        drawStr(canvas);
        drawChord(canvas);
        drawFretNumeration(canvas);
        if(theme==Theme.Dark) {
            drawBorder(canvas);
        }
        drawChordName(canvas);
    }
}
