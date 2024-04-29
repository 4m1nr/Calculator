//برنامه یک سری باگ جزئی داره که تو کلیت برنامه مشکلی ایجاد نمیکنه
//برای مثال یعد تقسیم بر صفر حتما باید صفحه کلیر بشه یا مثلا ترتیب خاصی از عملگر ها مشکل ایجاد میکنه
//به نظر نمیرسید هدف تمرین رفع این باگ ها هم باشه و راستش زمان بر بود
//ممنون میشم اگه این باگ هارو در نظر نگیرید

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;


public class  Main extends JFrame {
    static JButton activeOperator;
    public static void main(String[] args) {

        //frame config

        JFrame frame = new JFrame("Calculator");
        frame.setSize(263,380);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //numbers panel config

        JPanel numberPad = new JPanel(new GridLayout(4,3,5,5));
        numberPad.setBounds(5 , 95 , 180,240);


        //number buttons config

        JButton[] numberPadButton = new JButton[12];

        for (int i = 0; i < 9; i++) numberPadButton[i] = new JButton(Integer.toString(i+1));
        numberPadButton[9] = new JButton("±");
        numberPadButton[10]= new JButton("0");
        numberPadButton[11]= new JButton(".");

        for (int i = 0; i < 12 ; i++) numberPadButton[i].setBackground(Color.gray);

        for (int i = 0; i < 12 ; i++) numberPadButton[i].setFont(new Font(Font.SERIF,Font.BOLD,20));

        for (int i = 0; i < 12 ; i++) numberPad.add(numberPadButton[i]);

        //operators panel config

        JPanel operatorPad = new JPanel(new GridLayout(4,1,5,5));
        operatorPad.setBounds(5+180+5 , 95 , 53,240);

        //operator buttons config

        JButton[] operatorButton = new JButton[4];

        operatorButton[0] = new JButton("×");
        operatorButton[1] = new JButton("÷");
        operatorButton[2] = new JButton("+");
        operatorButton[3] = new JButton("-");

        for (int i = 0; i < 4 ; i++) operatorButton[i].setBackground(Color.white);

        for (int i = 0; i < 4 ; i++) operatorButton[i].setFont(new Font(Font.SERIF,Font.BOLD,20));

        for (int i = 0; i < 4 ; i++) operatorPad.add(operatorButton[i]);

        //tools panel config

        JPanel toolsPanel = new JPanel(new GridLayout(1,4,5,5));
        toolsPanel.setBounds(5 , 60, 180/3*4,30);

        //tool buttons config

        JButton[] toolsButton = new JButton[3];

        toolsButton[0] = new JButton("DEL");
        toolsButton[1] = new JButton("CLR");
        toolsButton[2] = new JButton("=");

        for (int i = 0; i < 3 ; i++) toolsButton[i].setFont(new Font(Font.SERIF,Font.BOLD,20));

        for (int i = 0; i < 3 ; i++) toolsPanel.add(toolsButton[i]);

        //display panel config

        JPanel displayPanel = new JPanel(new CardLayout());
        displayPanel.setBounds(5 , 5 , (180/3)*4,50);

        //input field config

        JTextField inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setAlignmentY(JTextField.BOTTOM);
        inputField.setEditable(false);
        inputField.setFont(new Font(Font.SERIF,Font.BOLD,15));

        //operation field config

        JLabel operationField = new JLabel();
        operationField.setHorizontalAlignment(JLabel.RIGHT);
        operationField.setBounds(0,2,180/3*4-10,20);
        operationField.setFont(new Font(Font.SERIF,Font.PLAIN,12));

        inputField.add(operationField);
        displayPanel.add(inputField);

        //number buttons actions

        numberPadButton[0].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[0].getText())));

        numberPadButton[1].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[1].getText())));

        numberPadButton[2].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[2].getText())));

        numberPadButton[3].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[3].getText())));

        numberPadButton[4].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[4].getText())));

        numberPadButton[5].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[5].getText())));

        numberPadButton[6].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[6].getText())));

        numberPadButton[7].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[7].getText())));

        numberPadButton[8].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[8].getText())));

        numberPadButton[10].addActionListener(e -> inputField.setText(inputField.getText().concat(numberPadButton[10].getText())));

        numberPadButton[11].addActionListener(e -> {
            if(!(inputField.getText().contains(".")))
                inputField.setText(inputField.getText().concat(numberPadButton[11].getText()));
        });

        numberPadButton[9].addActionListener(e -> {
            if(inputField.getText().contains("."))
                inputField.setText( String.valueOf(Double.parseDouble(inputField.getText()) * (-1) ));
            else inputField.setText( String.valueOf( Long.parseLong(inputField.getText()) * (-1) ));
            operationField.setText("");
        });

        //tool buttons actions

        toolsButton[0].addActionListener(e -> inputField.setText(inputField.getText().substring(0,inputField.getText().length()-1)));

        toolsButton[1].addActionListener(e -> {
            inputField.setText("");
            operationField.setText("");
        });

        toolsButton[2].addActionListener(e -> {
            operationField.setText(new Scanner(operationField.getText()).next() + "  " + activeOperator.getText() + "  " + inputField.getText() + "  " + "=");
            BigDecimal firstNumber  = BigDecimal.valueOf(new Scanner(operationField.getText()).nextDouble()),secondNumber = BigDecimal.valueOf(new Scanner(inputField.getText()).nextDouble());
            inputField.setText(operationFunction(firstNumber,secondNumber, activeOperator));
        });

        //operator buttons actions

        operatorButton[0].addActionListener(e -> {
            activeOperator = operatorButton[0];
            if(operationField.getText().isEmpty()) {
                operationField.setText(inputField.getText() + "  " + operatorButton[0].getText());
                inputField.setText("");
            } else if (inputField.getText().isEmpty()) {
                operationField.setText(operationField.getText().substring(0,operationField.getText().length()-1)+operatorButton[0].getText());
            }else {
                operationField.setText("");
                operatorButton[0].doClick();
            }
        });

        operatorButton[1].addActionListener(e -> {
            activeOperator = operatorButton[1];
            if(operationField.getText().isEmpty()) {
                operationField.setText(inputField.getText() + "  " + operatorButton[1].getText());
                inputField.setText("");
            } else if (inputField.getText().isEmpty()) {
                operationField.setText(operationField.getText().substring(0,operationField.getText().length()-1)+operatorButton[1].getText());
            }else {
                operationField.setText("");
                operatorButton[1].doClick();
            }
        });

        operatorButton[2].addActionListener(e -> {
            activeOperator = operatorButton[2];
            if(operationField.getText().isEmpty()) {
                operationField.setText(inputField.getText() + "  " + operatorButton[2].getText());
                inputField.setText("");
            } else if (inputField.getText().isEmpty()) {
                operationField.setText(operationField.getText().substring(0,operationField.getText().length()-1)+operatorButton[2].getText());
            }else {
                operationField.setText("");
                operatorButton[2].doClick();
            }
        });

        operatorButton[3].addActionListener(e -> {
            activeOperator = operatorButton[3];

            if(operationField.getText().isEmpty()) {
                operationField.setText(inputField.getText() + "  " + operatorButton[3].getText());
                inputField.setText("");
            } else if (inputField.getText().isEmpty()) {
                operationField.setText(operationField.getText().substring(0,operationField.getText().length()-1)+operatorButton[3].getText());
            }else {
                operationField.setText("");
                operatorButton[3].doClick();
            }
        });

        //final configuration

        frame.add(numberPad);
        frame.add(operatorPad);
        frame.add(displayPanel);
        frame.add(toolsPanel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

    }

    //operation function
    static String operationFunction(BigDecimal firstNumber, BigDecimal secondNumber, JButton activeOperator){
        BigDecimal answer = BigDecimal.valueOf(0);
        switch (activeOperator.getText()){
            case ("+"):
                answer = firstNumber.add(secondNumber);
                break;
            case ("-"):
                answer = firstNumber.add(secondNumber.negate());
                break;
            case ("÷"):
                try {
                    answer = firstNumber.divide(secondNumber ,RoundingMode.UNNECESSARY);
                }catch (ArithmeticException e) {
                    try {
                        answer = BigDecimal.valueOf(firstNumber.doubleValue() / secondNumber.doubleValue());
                    } catch (NumberFormatException E) {
                        return "cannot divide by zero";
                    }
                }
                break;
            case ("×"):
                answer = firstNumber.multiply(secondNumber);
                break;
        }

        try {
            return String.valueOf(answer.longValueExact());
        }
        catch (ArithmeticException e){
            return String.valueOf(answer);
        }
    }
}