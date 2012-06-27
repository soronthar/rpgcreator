package spikes;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 6/13/12
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Json {

//    private static class MySecManager extends SecurityManager {
//        @Override
//        public void checkPermission(Permission perm) {
//            System.out.println("Json$MySecManager.checkPermission:20 - " + perm);
//            super.checkPermission(perm);
//        }
//
//        @Override
//        public void checkPermission(Permission perm, Object context) {
//            System.out.println("Json$MySecManager.checkPermission:26 - " + perm + "  " + context);
//            super.checkPermission(perm, context);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkAccess(Thread t) {
//            System.out.println("Json$MySecManager.checkAccess:32");
//            super.checkAccess(t);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkAccess(ThreadGroup g) {
//            System.out.println("Json$MySecManager.checkAccess:38");
//            super.checkAccess(g);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkRead(FileDescriptor fd) {
//            System.out.println("Json$MySecManager.checkRead:44");
//            super.checkRead(fd);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkRead(String file) {
//            System.out.println("Json$MySecManager.checkRead:50");
//            super.checkRead(file);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkRead(String file, Object context) {
//            System.out.println("Json$MySecManager.checkRead:56");
//            super.checkRead(file, context);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkWrite(FileDescriptor fd) {
//            System.out.println("Json$MySecManager.checkWrite:62");
//            super.checkWrite(fd);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkWrite(String file) {
//            System.out.println("Json$MySecManager.checkWrite:68");
//            super.checkWrite(file);    //To change body of overridden methods use File | Settings | File Templates.
//        }

    private static class MySecManager extends SecurityManager {
        @Override
        public void checkPackageAccess(String pkg) {
            if (pkg.equals("java.io")) {
                Class[] classContext = getClassContext();
                for (Class aClass : classContext) {
                    if (aClass.getName().equals("my.package.with.MyClassLoader")) {
                        throw new SecurityException("Cannot access package java.io from extensions");
                    }
                }
            }
            System.out.println("Json$MySecManager.checkPackageAccess:85 - pkg = " + pkg);
            for (Class aClass : getClassContext()) {
                System.out.println("Json$MySecManager.checkPackageAccess:86 - aClass = " + aClass);
            }
            System.out.println("----");

                super.checkPackageAccess(pkg); //delegate to the default implementation

        }
    }
//        @Override
//        public void checkPackageDefinition(String pkg) {
//            System.out.println("Json$MySecManager.checkPackageDefinition:80");
//            super.checkPackageDefinition(pkg);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkSecurityAccess(String target) {
//            System.out.println("Json$MySecManager.checkSecurityAccess:86");
//            super.checkSecurityAccess(target);    //To change body of overridden methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void checkMemberAccess(Class<?> clazz, int which) {
//            System.out.println("Json$MySecManager.checkMemberAccess:92 - clazz = " + clazz);
//            super.checkMemberAccess(clazz, which);
//        }
//
//
//    }

    public static void main(String[] args) {

        System.setSecurityManager(new MySecManager());
        ClassLoader classLoader = Date.class.getClassLoader();
        System.out.println("Json.main:112 - Date = " + Date.class.getCanonicalName());
        System.out.println("Json.main:112 - Date = " + Date.class.getName());
        System.out.println("Json.main:103 - classLoader = " + classLoader);


//
//        The other path is to mess with SecurityManagers. AFAIK, the SecMan only checks for package access, not for individual classes. If you're ok with that, here is the code:
//
//        private static class MySecManager extends SecurityManager {
//            @Override
//            public void checkPackageAccess(String pkg) {
//                if (pkg.equals("java.io")) {
//                    Class[] classContext = getClassContext();
//                    boolean isExtension=false;
//                    for (Class aClass : classContext) {
//                        isExtension=isExtension || aClass.getName().equals("my.package.with.MyClassLoader";
//                    } //this loop can be optimized, but I'm feeling lazy.
//
//                    if (isExtension) {
//                        throw new SecurityException("Cannot access package java.io from extensions");
//                    }
//                }
//
//                super.checkPackageAccess(pkg); //delegate to the default implementation
//
//            }
//        }
//
//        The trick is to use a custom classloader for your extensions, so the SecMan can determine if a package is being invoked from an extension or from the engine.
//
//        To register your security manager you can either call System.setSecurityManager() during your app initialization, or set it in the command line.
//
//
//        java -Djava.security.manager=my.package.with.MySecMan MyApp
//
//

//
//        JsonReader jsonReader = new JsonReader() {
//            @Override
//            protected void startObject(String name) {
//                System.out.println("Json.startObject:19 - name = " + name);
//                super.startObject(name);
//            }
//
//            @Override
//            protected void startArray(String name) {
//                System.out.println("Json.startArray:25 - name = " + name);
//                super.startArray(name);    //To change body of overridden methods use File | Settings | File Templates.
//            }
//
//            @Override
//            protected void pop() {
//                System.out.println("Json.pop:31");
//                super.pop();    //To change body of overridden methods use File | Settings | File Templates.
//            }
//
//            @Override
//            protected void string(String name, String value) {
//                System.out.println("Json.string:37 - " +name + " = "+ value);
//                super.string(name, value);    //To change body of overridden methods use File | Settings | File Templates.
//            }
//
//            @Override
//            protected void number(String name, float value) {
//                System.out.println("Json.number:43 - " +name + " = "+ value);
//                super.number(name, value);    //To change body of overridden methods use File | Settings | File Templates.
//            }
//
//            @Override
//            protected void bool(String name, boolean value) {
//                System.out.println("Json.bool:49 - " +name + " = "+ value);
//                super.bool(name, value);    //To change body of overridden methods use File | Settings | File Templates.
//            }
//        };
//
//
//        Object parse = jsonReader.parse(TEST);
//        System.out.println("Json.main:22 - parse = " + parse);
    }


    private static final String TEST = "{\n" +
            "    \"name\":\"First Project\",\n" +
            "    \"tilesets\":[\n" +
            "        {\n" +
            "            \"A5\":\"Shop-TileA5.png\",\n" +
            "            \"stuff\":\"Town-Rural.png\",\n" +
            "            \"black\":\"Shop-Blacksmith.png\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"sceneries\":[\n" +
            "        {\n" +
            "            \"id\":\"1336686674947\",\n" +
            "            \"name\":\"Town\",\n" +
            "            \"size\":\"640x480\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\":\"1336692255933\",\n" +
            "            \"name\":\"Shop\",\n" +
            "            \"size\":\"288x256\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
