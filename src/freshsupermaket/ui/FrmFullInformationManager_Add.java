package freshsupermaket.ui;

import freshsupermaket.control.DiscountManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanFullInformation;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmFullInformationManager_Add extends JDialog implements ActionListener {
    BeanFullInformation pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    //    private JLabel labelId = new JLabel("优惠券编号");
    private JLabel labelContent = new JLabel("内容       ");
    private JLabel labelSuitable = new JLabel("适用商品数量");
    private JLabel labelDiscount = new JLabel("折扣      ");
    private JLabel labelStarteDate = new JLabel("起始日期   ");
    private JLabel labelEndDate = new JLabel("结束日期   ");

    //    private JTextField edtId=new JTextField(20);
    private JTextField edtContent=new JTextField(20);
    private JTextField edtSuitable=new JTextField(20);
    private JTextField edtDiscount=new JTextField(20);
    private JTextField edtStartDate=new JTextField(20);
    private JTextField edtEndDate=new JTextField(20);
    public FrmFullInformationManager_Add(JDialog f, String s, boolean b) {
        super(f, s, b);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labelContent);
        workPane.add(edtContent);
        workPane.add(labelSuitable);
        workPane.add(edtSuitable);
        workPane.add(labelDiscount);
        workPane.add(edtDiscount);
        workPane.add(labelStarteDate);
        workPane.add(edtStartDate);
        workPane.add(labelEndDate);
        workPane.add(edtEndDate);




        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(340, 400);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            this.pub=null;
            return;
        }
        else if(e.getSource()==this.btnOk){
            pub=new BeanFullInformation();
            pub.setContent(this.edtContent.getText());
            if(isNumeric.isNumeric(this.edtSuitable.getText())) {
                pub.setSuitable_good_quantity(Integer.valueOf(this.edtSuitable.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"满折数量为数字","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric1(this.edtDiscount.getText())&&Double.valueOf(this.edtDiscount.getText())>0&&Double.valueOf(this.edtDiscount.getText())<1) {
                pub.setDiscount(Double.valueOf(this.edtDiscount.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"折扣为0-1的数字","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                pub.setStart_date(sdf.parse(this.edtStartDate.getText()));
                pub.setEnd_date(sdf.parse(this.edtEndDate.getText()));
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(null,"请输入正确的时间格式","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }


            try {
                (new DiscountManager()).CreateFullInformation(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

    public BeanFullInformation getPub() {
        return pub;
    }
}
