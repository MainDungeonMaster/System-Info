package SystemPackage;

import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class SystemInfo {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();

        // grab time
        System.out.println("");
        System.out.println("Current Time: " + java.time.Clock.systemUTC().instant());

        // grab localHost Info
        System.out.println("");
        System.out.println("Local Host: " + localHost);
        System.out.println("IP Address: " + localHost.getHostAddress());
        System.out.println("Computer Name: " + localHost.getHostName());

        // grab OS
        System.out.println("");
        System.out.println("OS Name: " + System.getProperty("os.name"));
        System.out.println("OS Version: " + System.getProperty("os.version"));
        System.out.println("Architecture of The OS: " + System.getProperty("os.arch"));

        // grab data
        System.out.println("");
        System.out.println("Total memory available to JVM (GB): " +
                           (double)Runtime.getRuntime().totalMemory() / 1073741824);
        System.out.println("Free memory available to JVM (GB): " +
                           (double)Runtime.getRuntime().freeMemory() / 1073741824);
        System.out.println("Used Memory by JVM: " + (double)((double)Runtime.getRuntime().totalMemory() / 1073741824 - (double)Runtime.getRuntime().freeMemory() / 1073741824));
        System.out.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors());

        // access files *doesn't work on chrome*
        System.out.println("");
        File directory = new File("path/to/directory");
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();
            for(File file : files) {
                if(file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                } else {
                    System.out.println("File: " + file.getName());
                }
            }
        }
        
        // find details of current JVM version
        System.out.println("");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("User Home Directory: " + System.getProperty("user.home"));
        System.out.println("Java Class Path: " + System.getProperty("java.class.path")); //Does not work on chromeOS
        
        
        // grabs info about disc space
        System.out.println("");
        File[] roots = File.listRoots();
        
        for (File root : roots) {
            System.out.println("Drive: " + root);
            System.out.println("Total Disk Space (GB): " + (double) (root.getTotalSpace() / 1073741824));
            System.out.println("Free Disk Space (GB): " + (double) (root.getFreeSpace() / 1073741824));
            System.out.println("Usable Disk Space (GB): " + (double) (root.getUsableSpace() / 1073741824));
            System.out.println();
        }
        
        // grab current cpu usage
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double cpuUsage = osBean.getProcessCpuLoad() * 100;
        System.out.println("CPU Usage: " + cpuUsage + "% (work in progress)"); // always outputs -100%
        
        // grab current gpu usage (DOES NOT WORK)
        System.out.println("");
        try {
            Process process = Runtime.getRuntime().exec("nvidia-smi");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Utilization")) {
                    System.out.println("GPU Usage: " + line.split("\\s+")[2].replace("%", ""));
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // grab interface details
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                System.out.println("Interface Name: " + networkInterface.getName());
                System.out.println("Interface Display Name: " + networkInterface.getDisplayName());
                System.out.println("Interface MAC Address: " + networkInterface.getHardwareAddress());
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    System.out.println("IP Address: " + address.getHostAddress());

                }
            }
        } catch(SocketException e) {
            System.out.print("Interface Grab Failure: ");
            e.printStackTrace();
        }

    }


}

