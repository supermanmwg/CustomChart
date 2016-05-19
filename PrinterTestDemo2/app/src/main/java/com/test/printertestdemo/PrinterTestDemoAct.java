package com.test.printertestdemo;

import java.util.ArrayList;
import java.util.Random;

import woyou.aidlservice.jiuiv5.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PrinterTestDemoAct extends Activity implements OnClickListener {
	public static final int DO_PRINT = 0x10001;
	private Button b_barcode, b_pic, b_qcode, b_self, b_text, b_table, b_init,b_lines, b_test;
	private Button b_testall,b_erlmo,b_meituan,b_baidu,b_query,b_bytes;
	
	private IWoyouService woyouService;
	private BitmapUtils mBitmapUtils;
	
	private byte[] inputCommand ;
	
	private Runnable runA, runB, runC, runD, runE, runF, runG, runH, runI, runJ, runK;
	private ArrayList runArray = new ArrayList();
    
	private final int RUNNABLE_LENGHT = 11;
	
	private Random random = new Random();
	
	private ServiceConnection connService = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

			woyouService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			woyouService = IWoyouService.Stub.asInterface(service);
			setButtonEnable(true);
		}
	};

	private final int MSG_TEST = 1;
	private long printCount = 0;
	
	@SuppressLint("HandlerLeak")
	Handler handler=new Handler(){
	    @Override
	    public void handleMessage(Message msg){
	    	if(msg.what == MSG_TEST){
	    		test();
	    		handler.sendEmptyMessageDelayed(MSG_TEST, 800);
	    	}
	    }
	};
	
	private void test(){
		ThreadPoolManager.getInstance().executeTask(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					woyouService.printerSelfChecking(null);
					woyouService.printText(" printed: " + printCount + " bills.\n\n\n\n", null);
					printCount++;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_printer_test_demo);
		innitView();
	}

	
	private void innitView() {
		mBitmapUtils = new BitmapUtils(this);
		b_barcode = (Button) findViewById(R.id.b_barcode);
		b_pic = (Button) findViewById(R.id.b_pic);
		b_qcode = (Button) findViewById(R.id.b_qcode);
		b_self = (Button) findViewById(R.id.b_self);
		b_text = (Button) findViewById(R.id.b_text);
		b_table = (Button) findViewById(R.id.b_table);
		b_init = (Button) findViewById(R.id.b_init);
		b_lines = (Button) findViewById(R.id.b_lines);
		b_test = (Button) findViewById(R.id.b_test);
		
		findViewById(R.id.b_exit).setOnClickListener(this);
		
		b_barcode.setOnClickListener(this);
		b_pic.setOnClickListener(this);
		b_qcode.setOnClickListener(this);
		b_self.setOnClickListener(this);
		b_text.setOnClickListener(this);
		b_table.setOnClickListener(this);
		b_init.setOnClickListener(this);
		b_lines.setOnClickListener(this);
		b_test.setOnClickListener(this);
		
		b_bytes = (Button) findViewById(R.id.b_bytes);
		b_query = (Button) findViewById(R.id.b_query);
		b_baidu = (Button) findViewById(R.id.b_baidu);
		b_meituan = (Button) findViewById(R.id.b_meituan);
		b_erlmo = (Button) findViewById(R.id.b_erlmo);
		b_testall = (Button) findViewById(R.id.b_testall);

		
		b_bytes.setOnClickListener(this);
		b_query.setOnClickListener(this);
		b_baidu.setOnClickListener(this);
		b_meituan.setOnClickListener(this);
		b_erlmo.setOnClickListener(this);
		b_testall.setOnClickListener(this);
		
		setButtonEnable(false);
		
		Intent intent=new Intent();
		intent.setPackage("woyou.aidlservice.jiuiv5");
		intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
		startService(intent);
		bindService(intent, connService, Context.BIND_AUTO_CREATE);
				
		runA = new Runnable(){@Override public void run() { onClick(b_self);}};
		runB = new Runnable(){@Override public void run() { onClick(b_text);}};
		runC = new Runnable(){@Override public void run() { onClick(b_baidu);}};
		runD = new Runnable(){@Override public void run() { onClick(b_meituan);}};
		runE = new Runnable(){@Override public void run() { onClick(b_erlmo);}};
		runF = new Runnable(){@Override public void run() { onClick(b_table);}};
		runG = new Runnable(){@Override public void run() { onClick(b_barcode);}};
		runH = new Runnable(){@Override public void run() { onClick(b_qcode);}};
		runI = new Runnable(){@Override public void run() { onClick(b_query);}};
		runJ = new Runnable(){@Override public void run() { onClick(b_pic);}};
		runK = new Runnable(){@Override public void run() { onClick(b_test);}};
		
		runArray.add(runA);
		runArray.add(runB);
		runArray.add(runC);
		runArray.add(runD);
		runArray.add(runE);
		runArray.add(runF);
		runArray.add(runG);
		runArray.add(runH);
		runArray.add(runI);
		runArray.add(runJ);
		runArray.add(runK);
	}
	
	private void setButtonEnable(boolean flag){
		b_barcode.setEnabled(flag);
		b_pic.setEnabled(flag);
		b_qcode.setEnabled(flag);
		b_self.setEnabled(flag);
		b_text.setEnabled(flag);
		b_table.setEnabled(flag);
		b_init.setEnabled(flag);
		b_lines.setEnabled(flag);
		b_test.setEnabled(flag);

		b_bytes.setEnabled(flag);
		b_query.setEnabled(flag);
		b_baidu.setEnabled(flag);
		b_meituan.setEnabled(flag);
		b_erlmo.setEnabled(flag);
		b_testall.setEnabled(flag);
		
	}
	
	Bitmap mBitmap;
	Bitmap mBitmap1;

	@Override
	public void onClick(View v) {

			final ICallback callback = new ICallback.Stub() {
				
				@Override
				public void onRunResult(boolean success) throws RemoteException {
									
				}
				
				@Override
				public void onReturnString(final String value) throws RemoteException {
					
					System.out.println("printlength:" + value);						
/*					runOnUiThread(new Runnable(){
						@Override
						public void run() {
							Toast.makeText(getBaseContext(), "printlength:" + value, 100).show();							
						}});
*/				}
				
				@Override
				public void onRaiseException(int code, final String msg) throws RemoteException {
					System.out.println("onRaiseException: " + msg);
					runOnUiThread(new Runnable(){
						@Override
						public void run() {
							Toast.makeText(getBaseContext(), msg, 100).show();							
						}});
					
				}
			};
			
			switch (v.getId()) {
			case R.id.b_testall:				
				try{
					while(true){
						Runnable tmp = (Runnable)runArray.get(random.nextInt(RUNNABLE_LENGHT));
						runOnUiThread(tmp);
						Thread.sleep(10);
					}
				}catch(Exception e){}
				break;
			case R.id.b_erlmo:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.sendRAWData(getErlmoData(), callback);
							woyouService.lineWrap(4, callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				break;
			case R.id.b_meituan:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.sendRAWData(getMeituanBill(), callback);
							woyouService.lineWrap(2, callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				break;
			case R.id.b_baidu:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.sendRAWData(getBaiduTestBytes(), callback);
							woyouService.lineWrap(2, callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				break;
			case R.id.b_query:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.getPrintedLength(callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				break;
			case R.id.b_self:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.printerSelfChecking(callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				
				break;
			case R.id.b_lines:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.lineWrap(3, callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				
				break;
			case R.id.b_init:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.printerInit(callback);							
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				
				break;
			case R.id.b_text:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						try {
							//woyouService.setFontName("gh", null);
							
							woyouService.printText("您前面还有",callback);
							woyouService.printTextWithFont("28", "", 36, callback);
							woyouService.printText("位在等待\n",callback);
							for(int i=24; i<=48; i+=6){
								woyouService.printTextWithFont("商米", "", i, callback);
							}
							for(int i=48; i>=12; i-=2){
								woyouService.printTextWithFont("商米", "", i, callback);
							}
							woyouService.lineWrap(1, callback);							
							woyouService.printTextWithFont("ABCDEFGHIJKLMNOPQRSTUVWXYZ01234\n","",30, callback);							
							woyouService.printTextWithFont("abcdefghijklmnopqrstuvwxyz56789\n","",30, callback);
							woyouService.printText("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\n", callback);
							woyouService.printText("abcdefghijklmnopqrstuvwxyz0123456789\n",callback);
							woyouService.lineWrap(3, callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}});

				break;
			case R.id.b_table:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.setFontSize(24, callback);						
							String[] text = new String[4];
							int[] width = new int[] { 10, 6, 6, 8 };
							int[] align = new int[] { 0, 2, 2, 2 }; // 左齐,右齐,右齐,右齐
			
							text[0] = "菜品";
							text[1] = "数量";
							text[2] = "单价";
							text[3] = "金额";
							woyouService.printColumnsText(text, width, new int[] { 1, 1, 1, 1 }, callback);
							
							text[0] = "草莓酸奶布甸";
							text[1] = "4";
							text[2] = "12.00";
							text[3] = "48.00";
							woyouService.printColumnsText(text, width, align, callback);
							
							text[0] = "酸奶水果夹心面包";
							text[1] = "10";
							text[2] = "4.00";
							text[3] = "40.00";
							woyouService.printColumnsText(text, width, align, callback);
							
							text[0] = "酸奶水果布甸香橙软桃蛋糕"; // 文字超长,换行
							text[1] = "100";
							text[2] = "16.00";
							text[3] = "1600.00";
							woyouService.printColumnsText(text, width, align, callback);
			
							text[0] = "酸奶水果夹心面包";
							text[1] = "10";
							text[2] = "4.00";
							text[3] = "40.00";
							woyouService.printColumnsText(text, width, align, callback);
							
							woyouService.setFontSize(30, callback);	
							woyouService.setAlignment(0, callback);
							
							text = new String[3];
							width = new int[] { 10, 8, 10 };
							align = new int[] { 0, 2, 2 }; // 左齐,右齐,右齐,右齐
							
							text[0] = "菜品";
							text[1] = "数量";
							text[2] = "金额";
							woyouService.printColumnsText(text, width, new int[] { 0, 0,0}, callback);
							
							text[0] = "草莓酸奶布甸";
							text[1] = "4";
							text[2] = "48.00";
							woyouService.printColumnsText(text, width, align, callback);
							
							text[0] = "酸奶水果夹心面包";
							text[1] = "10";
							text[2] = "40.00";
							woyouService.printColumnsText(text, width, align, callback);
							
							text[0] = "酸奶水果布甸香橙软桃蛋糕"; // 文字超长,换行
							text[1] = "100";
							text[2] = "1600.00";
							woyouService.printColumnsText(text, width, align, callback);
			
							text[0] = "酸奶水果夹心面包";
							text[1] = "10";
							text[2] = "40.00";
							woyouService.printColumnsText(text, width, align, callback);							
							woyouService.lineWrap(3, null);
							
							woyouService.sendRAWData(new byte[]{0x1B, 0x24, (byte)0, 0, 0x41,0x42, 0x0A}, callback );							
							woyouService.sendRAWData(new byte[]{0x1B, 0x24, (byte)30, 0, 0x41,0x42, 0x0A}, callback );
							woyouService.sendRAWData(new byte[]{0x1B, 0x24, (byte)60, 0, 0x41,0x42, 0x0A}, callback );
							woyouService.sendRAWData(new byte[]{0x1B, 0x24, (byte)90, 0, 0x41,0x42, 0x0A}, callback );
							woyouService.sendRAWData(new byte[]{0x1B, 0x24, (byte)120, 0, 0x41,0x42, 0x0A}, callback );
							woyouService.sendRAWData(new byte[]{0x1B, 0x24, (byte)150, 0, 0x41,0x42, 0x0A}, callback );
							
							
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}});

				
				break;
			case R.id.b_pic:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						if( mBitmap == null ){
							mBitmap = BitmapFactory.decodeResource(getResources(), R.raw.test);
						}
						if( mBitmap1 == null ){
							mBitmap1 = BitmapUtils.zoomBitmap(mBitmap, 300, 300);
						}
						
						try {
							woyouService.printBitmap(mBitmap, callback);
							woyouService.printBitmap(mBitmap1, callback);
							woyouService.lineWrap(3, null);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}});
				break;
			case R.id.b_barcode:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.printBarCode("2015112910", 8, 120, 2, 2, callback);
							woyouService.lineWrap(3, callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				
				break;
			case R.id.b_qcode:
				ThreadPoolManager.getInstance().executeTask(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							woyouService.printQRCode("http://www.woyouwaimai.com", 10, 2, callback);
							woyouService.lineWrap(3, callback);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				
				
				break;
			case R.id.b_test:
				//test();
				//printCount = 1;
				//handler.sendEmptyMessage(MSG_TEST);
				try {
					//打印阶梯黑块
					woyouService.sendRAWData(BytesUtil.initBlackBlock(384), callback);
					//woyouService.lineWrap(1, null);
					
					//打印黑块
					woyouService.sendRAWData(BytesUtil.initBlackBlock(48, 384), callback);
					woyouService.lineWrap(1, callback);
					
					//打印灰块
					woyouService.sendRAWData(BytesUtil.initGrayBlock(48, 384), callback);
					//woyouService.lineWrap(1, null);
					
					//打印表格
					woyouService.sendRAWData(BytesUtil.initTable(3, 12), callback);
					//woyouService.lineWrap(1, null);
					
					Bitmap bmp = BytesUtil.getTableBitmapFromData(24, 48, 8);
					woyouService.printBitmap(bmp, callback);
					woyouService.lineWrap(3, callback);
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case R.id.b_exit:
				try{
					unbindService(connService);					
					handler.removeMessages(MSG_TEST);
				}catch(Exception e){}
				finally{
					this.finish();
				}
				break;
			case R.id.b_bytes:

			      final EditText inputServer = new EditText(this);
			        AlertDialog.Builder builder = new AlertDialog.Builder(this);
			        builder.setTitle("Server").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
			                .setNegativeButton("Cancel", null);
			        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			            public void onClick(DialogInterface dialog, int which) {
			            	inputCommand = BytesUtil.getBytesFromHexString(inputServer.getText().toString());
			            	try{
			            		woyouService.sendRAWData(inputCommand, callback);
			            	} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            }
			        });
			        builder.show();				
				break;
			default:
				break;
			}

	}
	
	//饿了么小票
	public byte[] getErlmoData(){
		byte[] rv = new byte[]{
				0x1b,0x61,0x00,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x20,0x1d,0x21,0x11,0x23,0x31,0x1d,0x21,0x00,0x00,(byte)0xb6,(byte)0xf6
				,(byte)0xc1,(byte)0xcb,(byte)0xc3,(byte)0xb4,(byte)0xcd,(byte)(byte)0xe2,(byte)0xc2,(byte)0xf4,(byte)0xb5,(byte)0xa5,0x20,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x0a,0x0a,0x1b,0x61
				,0x01,(byte)0xbf,(byte)0xa8,(byte)0xc8,(byte)0xf8,(byte)0xc5,(byte)0xfb,(byte)0xc8,(byte)0xf8,0x0a,0x0a,0x1b,0x61,0x00,0x1b,0x61,0x01,0x1d,0x21,0x11,0x2d,0x2d
				,(byte)0xd2,(byte)0xd1,(byte)0xd6,(byte)0xa7,(byte)0xb8,(byte)0xb6,0x2d,0x2d,0x1d,0x21,0x00,0x00,0x0a,0x0a,0x1b,0x61,0x00,0x1b,0x61,0x01,0x1d,0x21
				,0x11,(byte)0xd4,(byte)0xa4,(byte)0xbc,(byte)0xc6,0x31,0x39,0x3a,0x30,0x30,(byte)0xcb,(byte)0xcd,(byte)0xb4,(byte)(byte)0xef,0x1d,0x21,0x00,0x00,0x0a,0x0a,0x1b,0x61
				,0x00,0x5b,(byte)0xcf,(byte)0xc2,(byte)0xb5,(byte)0xa5,(byte)0xca,(byte)0xb1,(byte)0xbc,(byte)(byte)0xe4,0x5d,0x32,0x30,0x31,0x34,0x2d,0x31,0x32,0x2d,0x30,0x33,0x20
				,0x31,0x36,0x3a,0x32,0x31,0x0a,0x5b,(byte)0xb1,(byte)0xb8,(byte)0xd7,(byte)0xa2,0x5d,0x1d,0x21,0x01,(byte)0xb2,(byte)0xbb,(byte)0xb3,(byte)0xd4,(byte)0xc0,(byte)0xb1,0x20
				,(byte)0xc0,(byte)0xb1,(byte)0xd2,(byte)0xbb,(byte)0xb5,(byte)(byte)0xe3,0x20,(byte)0xb6,(byte)(byte)0xe0,(byte)0xbc,(byte)0xd3,(byte)0xc3,(byte)0xd7,0x20,(byte)0xc3,(byte)0xbb,(byte)0xc1,(byte)(byte)0xe3,(byte)0xc7,(byte)0xae,0x1d,0x21
				,0x00,0x0a,0x5b,(byte)0xb7,(byte)0xa2,(byte)0xc6,(byte)0xb1,0x5d,(byte)0xd5,(byte)(byte)0xe2,(byte)0xca,(byte)0xc7,(byte)0xd2,(byte)0xbb,(byte)0xb8,(byte)0xf6,(byte)0xb7,(byte)0xa2,(byte)0xc6,(byte)0xb1,(byte)0xcc,(byte)0xa7
				,(byte)0xcd,(byte)0xb7,0x0a,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,(byte)0xb2,(byte)0xcb,(byte)0xc3,(byte)0xfb,0x20,0x20,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,(byte)0xca,(byte)0xfd,(byte)0xc1,(byte)0xbf,0x20,0x20,(byte)0xd0,(byte)0xa1
				,(byte)0xbc,(byte)0xc6,0x0a,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x20,0x31,(byte)0xba,(byte)0xc5,(byte)0xc0,(byte)0xba,(byte)0xd7,(byte)0xd3
				,0x20,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x1d,0x21,0x01,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,(byte)0xc3,(byte)0xc0
				,(byte)0xca,(byte)0xb3,(byte)0xd2,(byte)0xbb,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x1d,0x21
				,0x01,0x20,0x78,0x34,0x1d,0x21,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x34
				,0x1d,0x21,0x00,0x0a,0x1d,0x21,0x01,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,(byte)0xc3,(byte)0xc0,(byte)0xca,(byte)0xb3,(byte)0xb6,(byte)0xfe,0x1d,0x21,0x00,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x20,0x78,0x36,0x1d,0x21,0x00,0x1d,0x21
				,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x36,0x1d,0x21,0x00,0x0a,0x1d,0x21,0x01,(byte)0xb2,(byte)(byte)0xe2
				,(byte)0xca,(byte)0xd4,(byte)0xc3,(byte)0xc0,(byte)0xca,(byte)0xb3,(byte)0xc8,(byte)0xfd,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20
				,0x20,0x20,0x1d,0x21,0x01,0x20,0x78,0x32,0x1d,0x21,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20
				,0x1d,0x21,0x01,0x32,0x1d,0x21,0x00,0x0a,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x20,0x32,(byte)0xba
				,(byte)0xc5,(byte)0xc0,(byte)0xba,(byte)0xd7,(byte)0xd3,0x20,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x1d,0x21,0x01,(byte)0xb2
				,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,0x31,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20
				,0x20,0x20,0x20,0x1d,0x21,0x01,0x20,0x78,0x31,0x1d,0x21,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20
				,0x20,0x1d,0x21,0x01,0x31,0x1d,0x21,0x00,0x0a,0x1d,0x21,0x01,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,0x32,0x1d,0x21,0x00,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x20,0x78,0x31
				,0x1d,0x21,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x31,0x1d,0x21,0x00,0x0a
				,0x1d,0x21,0x01,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,0x33,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x20,0x78,0x31,0x1d,0x21,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00
				,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x32,0x33,0x1d,0x21,0x00,0x0a,0x1d,0x21,0x01,0x28,0x2b,0x29,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca
				,(byte)0xd4,(byte)0xd1,(byte)0xf3,(byte)0xc6,(byte)0xf8,(byte)0xa4,(byte)0xce,(byte)0xce,(byte)0xf7,(byte)0xca,(byte)0xbd,(byte)0xcc,(byte)0xf0,(byte)0xb5,(byte)(byte)0xe3,0x1d,0x21,0x00,0x20,0x20,0x1d,0x21
				,0x01,0x20,0x78,0x31,0x1d,0x21,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x31
				,0x1d,0x21,0x00,0x0a,0x1d,0x21,0x01,0x28,0x2b,0x29,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,(byte)0xcb,(byte)(byte)0xe1,(byte)0xc0,(byte)0xb1,(byte)0xc4,(byte)0xbe,(byte)0xb6,(byte)0xfa
				,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x20,0x78,0x31,0x1d,0x21,0x00,0x1d,0x21
				,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x38,0x1d,0x21,0x00,0x0a,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x20,0x33,(byte)0xba,(byte)0xc5,(byte)0xc0,(byte)0xba,(byte)0xd7,(byte)0xd3,0x20,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x0a,0x1d,0x21,0x01,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,(byte)0xb2,(byte)0xcb,(byte)0xc6,(byte)0xb7,(byte)0xc3,(byte)0xfb,(byte)0xd7,(byte)0xd6,(byte)0xba,(byte)0xdc
				,(byte)0xb3,(byte)0xa4,(byte)0xba,(byte)0xdc,(byte)0xb3,(byte)0xa4,(byte)0xba,(byte)0xdc,(byte)0xb3,(byte)0xa4,(byte)0xba,(byte)0xdc,(byte)0xb3,(byte)0xa4,(byte)0xba,(byte)0xdc,(byte)0xb3,(byte)0xa4,0x1d,0x21,0x00,0x0a
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20
				,0x20,0x1d,0x21,0x01,0x20,0x78,0x31,0x1d,0x21,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x1d,0x21,0x01
				,0x33,0x30,0x30,0x1d,0x21,0x00,0x0a,0x1d,0x21,0x01,(byte)0xb2,(byte)(byte)0xe2,(byte)0xca,(byte)0xd4,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x20,0x78,0x31,0x1d,0x21
				,0x00,0x1d,0x21,0x01,0x1d,0x21,0x00,0x20,0x20,0x20,0x20,0x20,0x1d,0x21,0x01,0x31,0x1d,0x21,0x00,0x0a,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x20,(byte)0xc6,(byte)(byte)0xe4,(byte)0xcb,(byte)0xfb,(byte)0xb7,(byte)0xd1,(byte)0xd3,(byte)0xc3,0x20,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x1d,0x21,0x01,(byte)0xc5,(byte)(byte)0xe4,(byte)0xcb,(byte)0xcd,(byte)0xb7,(byte)0xd1,0x1d,0x21,0x00,0x20
				,0x20,0x20,0x20,0x20
		};
		return rv;
	}	
	
	//百度小票
	public byte[] getBaiduTestBytes(){
		byte[] rv = new byte[]{
				0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x11,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x1b,0x45,0x01,0x1b,0x47,0x01,(byte)0xb1,(byte)0xbe
				,(byte)0xb5,(byte)0xea,(byte)0xc1,(byte)0xf4,(byte)0xb4,(byte)0xe6,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2a,0x2a,0x2a
				,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a
				,0x2a,0x0a
				,0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x11,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x1b,0x45,0x01,0x1b,0x47,0x01,0x1b,0x61
				,0x01,0x23,0x31,0x35,0x20,(byte)0xb0,(byte)0xd9,(byte)0xb6,(byte)0xc8,(byte)0xcd,(byte)0xe2,(byte)0xc2,(byte)0xf4,0x0a,0x5b,(byte)0xbb,(byte)0xf5,(byte)0xb5,(byte)0xbd,(byte)0xb8,(byte)0xb6,(byte)0xbf,(byte)0xee,0x5d,0x0a,0x1b,0x4d,0x00
				,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a
				,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x0a
				,0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x01,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,(byte)0xc6,(byte)0xda,(byte)0xcd,(byte)0xfb,(byte)0xcb,(byte)0xcd,(byte)0xb4,(byte)0xef
				,(byte)0xca,(byte)0xb1,(byte)0xbc,(byte)0xe4,(byte)0xa3,(byte)0xba,(byte)0xc1,(byte)0xa2,(byte)0xbc,(byte)0xb4,(byte)0xc5,(byte)0xe4,(byte)0xcb,(byte)0xcd,0x0a,(byte)0xb6,(byte)0xa9,(byte)0xb5,(byte)0xa5,(byte)0xb1,(byte)0xb8,(byte)0xd7,(byte)0xa2,(byte)0xa3,(byte)0xba,(byte)0xc7,(byte)0xeb,(byte)0xcb
				,(byte)0xcd,(byte)0xb5,(byte)0xbd,(byte)0xbf,(byte)0xfc,(byte)0xbf,(byte)0xc6,(byte)0xce,(byte)0xf7,(byte)0xc3,(byte)0xc5,0x2c,(byte)0xb2,(byte)0xbb,(byte)0xd2,(byte)0xaa,(byte)0xc0,(byte)0xb1,0x0a,(byte)0xb7,(byte)0xa2,(byte)0xc6,(byte)0xb1,(byte)0xd0,(byte)0xc5,(byte)0xcf,(byte)0xa2,(byte)0xa3
				,(byte)0xba,(byte)0xb0,(byte)0xd9,(byte)0xb6,(byte)0xc8,(byte)0xcd,(byte)0xe2,(byte)0xc2,(byte)0xf4,(byte)0xb7,(byte)0xa2,(byte)0xc6,(byte)0xb1,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47
				,0x00,0x1b,0x61,0x00,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a
				,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x0a
				,0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,(byte)0xb6,(byte)0xa9,(byte)0xb5,(byte)0xa5,(byte)0xb1,(byte)0xe0,(byte)0xba,(byte)0xc5
				,(byte)0xa3,(byte)0xba,0x31,0x34,0x31,0x38,0x37,0x31,0x38,0x36,0x39,0x31,0x31,0x36,0x38,0x39,0x0a,(byte)0xcf,(byte)0xc2,(byte)0xb5,(byte)0xa5,(byte)0xca,(byte)0xb1,(byte)0xbc,(byte)0xe4,(byte)0xa3,(byte)0xba,0x32
				,0x30,0x31,0x34,0x2d,0x31,0x32,0x2d,0x31,0x36,0x20,0x31,0x36,0x3a,0x33,0x31,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00
				,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a
				,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x0a
				,0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x01,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,(byte)0xb2,(byte)0xcb,(byte)0xc6,(byte)0xb7,(byte)0xc3,(byte)0xfb,(byte)0xb3,(byte)0xc6
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,(byte)0xca,(byte)0xfd,(byte)0xc1,(byte)0xbf,0x20,0x20,0x20,0x20,0x20,(byte)0xbd,(byte)0xf0,(byte)0xb6,(byte)0xee,0x0a,0x1b,0x4d,0x00,0x1b,0x61
				,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x01,0x1b
				,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,(byte)0xcf,(byte)0xe3,(byte)0xc0,(byte)0xb1,(byte)0xc3,(byte)0xe6,(byte)0xcc,(byte)0xd7,(byte)0xb2,(byte)0xcd,0x1b,0x24,(byte)0xf2,0x00,0x31,0x1b,0x24,0x25,0x01,(byte)0xa3
				,(byte)0xa4,0x34,0x30,0x2e,0x30,0x30,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x1b,0x4d,0x00
				,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x01,0x1b,0x45,0x00,0x1b
				,0x47,0x00,0x1b,0x61,0x00,(byte)0xcb,(byte)0xd8,(byte)0xca,(byte)0xb3,(byte)0xcc,(byte)0xec,(byte)0xcf,(byte)0xc2,(byte)0xba,(byte)0xba,(byte)0xb1,(byte)0xa4,0x1b,0x24,(byte)0xf2,0x00,0x31,0x1b,0x24,0x25,0x01,(byte)0xa3,(byte)0xa4
				,0x33,0x38,0x2e,0x30,0x30,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x1b,0x4d,0x00,0x1b
				,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00
				,0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a
				,0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a
				,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x0a,0x1b,0x4d,0x00
				,0x1b,0x61,0x00,0x1d,0x21,0x01,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,(byte)0xd0,(byte)0xd5,(byte)0xc3,(byte)0xfb,(byte)0xa3,(byte)0xba,(byte)0xb0,(byte)0xd9,(byte)0xb6,(byte)0xc8,(byte)0xb2,(byte)0xe2,(byte)0xca
				,(byte)0xd4,0x0a,(byte)0xb5,(byte)0xd8,(byte)0xd6,(byte)0xb7,(byte)0xa3,(byte)0xba,(byte)0xbf,(byte)0xfc,(byte)0xbf,(byte)0xc6,(byte)0xbf,(byte)0xc6,(byte)0xbc,(byte)0xbc,(byte)0xb4,(byte)0xf3,(byte)0xcf,(byte)0xc3,0x0a,(byte)0xb5,(byte)0xe7,(byte)0xbb,(byte)0xb0,(byte)0xa3,(byte)0xba,0x31
				,0x38,0x37,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x0a
				,0x1b,0x40,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a
				,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x0a,(byte)0xb0,(byte)0xd9,(byte)0xb6
				,(byte)0xc8,(byte)0xb2,(byte)0xe2,(byte)0xca,(byte)0xd4,(byte)0xc9,(byte)0xcc,(byte)0xbb,(byte)0xa7,0x0a,0x31,0x38,0x37,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d
				,0x21,0x00,0x1b,0x45,0x00,0x1b,0x47,0x00,0x1b,0x61,0x00,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a
				,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x2a,0x0a,0x1b,0x4d,0x00,0x1b,0x61,0x00,0x1d,0x21,0x00,0x1b,0x45,0x00
				,0x1b,0x47,0x00,0x1b,0x61,0x00,0x1b,0x61,0x01,0x23,0x31,0x35,0x20,(byte)0xb0,(byte)0xd9,(byte)0xb6,(byte)0xc8,(byte)0xcd,(byte)0xe2,(byte)0xc2,(byte)0xf4,0x20,0x20,0x31,0x31,(byte)0xd4,(byte)0xc2,0x30
				,0x39,(byte)0xc8,(byte)0xd5,0x20,0x31,0x37,0x3a,0x35,0x30,0x3a,0x33,0x30,0x0a,0x0a,0x0a,0x0a,0x0a
		};
		return rv;
	}
	
	public byte[] getMeituanBill(){
		byte[] rv = new byte[]{
				0x1b,0x40,0x1b,0x61,0x01,0x1d,0x21,0x11,(byte)0xa3,(byte)0xa3,0x31,0x20,0x20,(byte)0xc3,(byte)0xc0,(byte)0xcd,(byte)0xc5,(byte)0xb2,(byte)0xe2,(byte)0xca,(byte)0xd4,0x0a
				,0x0a,0x1d,0x21,0x00,(byte)0xd4,(byte)0xc1,(byte)0xcf,(byte)0xe3,(byte)0xb8,(byte)0xdb,(byte)0xca,(byte)0xbd,(byte)0xc9,(byte)0xd5,(byte)0xc0,(byte)0xb0,0x28,(byte)0xb5,(byte)0xda,0x31,(byte)0xc1,(byte)0xaa
				,0x29,0x0a,0x1b,0x21,0x10,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x2a,0x20,0x2a,0x20,0x2a,0x20
				,0x2a,0x20,0x2a,0x20,0x2a,0x20,0x20,(byte)0xd4,(byte)0xa4,(byte)0xb6,(byte)0xa9,(byte)0xb5,(byte)0xa5,0x20,0x20,0x2a,0x20,0x2a,0x20,0x2a,0x20,0x2a
				,0x20,0x2a,0x20,0x2a,0x0a,(byte)0xc6,(byte)0xda,(byte)0xcd,(byte)0xfb,(byte)0xcb,(byte)0xcd,(byte)0xb4,(byte)0xef,(byte)0xca,(byte)0xb1,(byte)0xbc,(byte)0xe4,0x3a,0x20,0x5b,0x31,0x38
				,0x3a,0x30,0x30,0x5d,0x0a,0x1d,0x21,0x00,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x1b,0x61,0x00
				,(byte)0xcf,(byte)0xc2,(byte)0xb5,(byte)0xa5,(byte)0xca,(byte)0xb1,(byte)0xbc,(byte)0xe4,0x3a,0x30,0x31,0x2d,0x30,0x31,0x20,0x31,0x32,0x3a,0x30,0x30,0x0a,0x1b
				,0x21,0x10,(byte)0xb1,(byte)0xb8,(byte)0xd7,(byte)0xa2,0x3a,(byte)0xb1,(byte)0xf0,(byte)0xcc,(byte)0xab,(byte)0xc0,(byte)0xb1,0x0a,0x1d,0x21,0x00,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,(byte)0xb2,(byte)0xcb,(byte)0xc3,(byte)0xfb,0x09,0x09,0x20,0x20,0x20,(byte)0xca,(byte)0xfd,(byte)0xc1,(byte)0xbf,0x09,0x20,0x20
				,0x20,0x20,(byte)0xd0,(byte)0xa1,(byte)0xbc,(byte)0xc6,0x09,0x0a,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x1b,0x21,0x10
				,(byte)0xba,(byte)0xec,(byte)0xc9,(byte)0xd5,(byte)0xc8,(byte)0xe2,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x78
				,0x31,0x09,0x20,0x20,0x20,0x20,0x20,0x20,0x31,0x32,0x0a,0x1d,0x21,0x00,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x0a,(byte)0xc5,(byte)0xe4,(byte)0xcb,(byte)0xcd,(byte)0xb7,(byte)0xd1,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x35,0x0a,(byte)0xb2,(byte)0xcd,(byte)0xba,(byte)0xd0,(byte)0xb7,(byte)0xd1,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20
				,0x20,0x31,0x0a,0x5b,(byte)0xb3,(byte)0xac,(byte)0xca,(byte)0xb1,(byte)0xc5,(byte)0xe2,(byte)0xb8,(byte)0xb6,0x5d,0x20,0x2d,(byte)0xcf,(byte)0xea,(byte)0xbc,(byte)0xfb,(byte)0xb6,(byte)0xa9,(byte)0xb5
				,(byte)0xa5,0x0a,(byte)0xbf,(byte)0xc9,(byte)0xbf,(byte)0xda,(byte)0xbf,(byte)0xc9,(byte)0xc0,(byte)0xd6,0x3a,0x78,0x31,0x0a,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x0a,0x1b,0x21,0x10,(byte)0xba,(byte)0xcf,(byte)0xbc,(byte)0xc6,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20
				,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x31,0x38,(byte)0xd4,(byte)0xaa,0x0a,0x1b,0x40,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x1d,0x21,0x11,(byte)0xd5,(byte)0xc5,0x2a,0x20,0x31,0x38,0x33,0x31,0x32,0x33,0x34
				,0x35,0x36,0x37,0x38,0x0a,(byte)0xb5,(byte)0xd8,(byte)0xd6,(byte)0xb7,(byte)0xd0,(byte)0xc5,(byte)0xcf,(byte)0xa2,0x0a,0x1d,0x21,0x00,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d
				,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a,0x0a,0x1b,0x40,0x1b,0x61,0x01,0x1d,0x21,0x11,(byte)0xa3,(byte)0xa3,0x31,0x20,0x20,(byte)0xc3,(byte)0xc0
				,(byte)0xcd,(byte)0xc5,(byte)0xb2,(byte)0xe2,(byte)0xca,(byte)0xd4,0x0a,0x1d,0x21,0x00,0x1b,0x40,0x0a,0x0a,0x0a,0x1d,0x56,0x00				
		};
		return rv;
	}
}
