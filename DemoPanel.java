package gui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.Message;
import gui.DemoPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DemoPanel extends JPanel{
 
	private JLabel labelUser, labelPassWd;            //锟斤拷签      锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷
	private JButton buttonLogin,buttonReceiver;                      //锟斤拷钮      锟斤拷录
	private JTextField textFieldUserName;             //锟侥憋拷锟斤拷  锟矫伙拷锟斤拷锟斤拷锟斤拷 
	private JTextField passWdField;               //锟斤拷锟斤拷锟�  锟斤拷锟斤拷锟斤拷锟斤拷
    private JPanel panelUserName;
    private JPanel panelPassWd;
    private JPanel panelLoginButton;
	
    public DemoPanel(){
    	this.labelUser = new JLabel("Sender");
    	this.labelPassWd = new JLabel("Reciever");
    	this.buttonLogin = new JButton("Send");
    	this.buttonReceiver=new JButton("Receiver");
    	this.textFieldUserName = new JTextField(10);
    	this.passWdField = new JTextField(10);
    	this.panelPassWd = new JPanel();
    	this.panelUserName = new JPanel();
    	this.panelLoginButton = new JPanel();
    	
    	buttonLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CloudAccount account = new CloudAccount(
		                ServiceSettings.getMNSAccessKeyId(),
		                ServiceSettings.getMNSAccessKeySecret(),
		                ServiceSettings.getMNSAccountEndpoint());
		        MNSClient client = account.getMNSClient(); //this client need only initialize once
		        //鍙戦��
		        try{
		            CloudQueue queue = client.getQueueRef("lovelyMillenniumax");// replace with your queue name
		                Message message = new Message();
		                message.setMessageBody(textFieldUserName.getText()); // use your own message body here
		                Message putMsg = queue.putMessage(message);
		                System.out.println("Send message id is: " + putMsg.getMessageId());
		        } catch (ClientException ce)
		        {
		            System.out.println("Something wrong with the network connection between client and MNS service."
		                    + "Please check your network and DNS availablity.");
		            ce.printStackTrace();
		        } catch (ServiceException se)
		        {
		            if (se.getErrorCode().equals("QueueNotExist"))
		            {
		                System.out.println("Queue is not exist.Please create before use");
		            } else if (se.getErrorCode().equals("TimeExpired"))
		            {
		                System.out.println("The request is time expired. Please check your local machine timeclock");
		            }
		            se.printStackTrace();
		        } catch (Exception e1)
		        {
		            System.out.println("Unknown exception happened!");
		            e1.printStackTrace();	
		        }
		        client.close();
			}
    	});
    	//鎺ユ敹淇″績鐨勬寜閽搷浣�
    	buttonReceiver.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CloudAccount account = new CloudAccount(
		                ServiceSettings.getMNSAccessKeyId(),
		                ServiceSettings.getMNSAccessKeySecret(),
		                ServiceSettings.getMNSAccountEndpoint());
		        MNSClient client = account.getMNSClient(); 
		        try{
		        	
		            CloudQueue queue = client.getQueueRef("lovelyMillenniumax");// replace with your queue name
		         
		                Message popMsg = queue.popMessage();
		                if(popMsg==null) {
		                	System.out.println("娑堟伅宸茶瀹屽暒");
		                }
		                if (popMsg != null){
		                    passWdField.setText(popMsg.getMessageBodyAsString());
		                    queue.deleteMessage(popMsg.getReceiptHandle());
		                    //System.out.println("delete message successfully.\n");
		            }
		                System.out.println("鎺ユ敹鎴愬姛");
		        } catch (ClientException ce)
		        {
		            System.out.println("Something wrong with the network connection between client and MNS service."
		                    + "Please check your network and DNS availablity.");
		            ce.printStackTrace();
		        } catch (ServiceException se)
		        {
		            if (se.getErrorCode().equals("QueueNotExist"))
		            {
		                System.out.println("Queue is not exist.Please create queue before use");
		            } else if (se.getErrorCode().equals("TimeExpired"))
		            {
		                System.out.println("The request is time expired. Please check your local machine timeclock");
		            }
		            /*
		            you can get more MNS service error code in following link.
		            https://help.aliyun.com/document_detail/mns/api_reference/error_code/error_code.html?spm=5176.docmns/api_reference/error_code/error_response
		            */
		            se.printStackTrace();
		        } 
		        catch (Exception e1)
		        {
		            System.out.println("Unknown exception happened!");
		            e1.printStackTrace();
		        }
				client.close();
}
    	});
    	this.setLayout(new GridLayout(3, 1));  //锟斤拷锟斤拷式锟斤拷锟斤拷
    	this.panelUserName.add(this.labelUser);
    	this.panelUserName.add(this.textFieldUserName);
    	this.panelPassWd.add(this.labelPassWd);
    	this.panelPassWd.add(this.passWdField);
    	this.panelLoginButton.add(buttonLogin);
    	this.panelLoginButton.add(buttonReceiver);
    	this.add(this.panelUserName);
    	this.add(this.panelPassWd);
    	this.add(this.panelLoginButton);
    }
}

