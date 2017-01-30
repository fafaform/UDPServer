//package udpserver;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//
//public class UDPServer implements Runnable{
//
////    private static int count = 0;
//    private String message;
//    private Boolean running = true;
//       
//    public UDPServer() {
//        
//    }
//    
//    @Override
//    public void run() {
//        try {
//            DatagramSocket serverSocket = new DatagramSocket(9876);
//
//            while(running)
//            {
//                byte[] receiveData = new byte[1024];
//                byte[] sendData = new byte[1024];
//                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//                serverSocket.receive(receivePacket);
////                String sentence = new String( receivePacket.getData());
//                message = new String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength());
////                count++;
////                System.out.println("RECEIVED: " + message + " " + count);
//                InetAddress IPAddress = receivePacket.getAddress();
//                int port = receivePacket.getPort();
//                String capitalizedSentence = message.toUpperCase();
////                System.out.println(capitalizedSentence);
//                sendData = capitalizedSentence.getBytes();
//                DatagramPacket sendPacket =
//                        new DatagramPacket(sendData, sendData.length, IPAddress, port);
//                serverSocket.send(sendPacket);
//            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setRunning(Boolean tf){
//        running = tf;
//    }
//    
//    public boolean getRunning(){
//        return running;
//    }
//    
//    public String getMessage(){
//        return message;
//    }
//}
