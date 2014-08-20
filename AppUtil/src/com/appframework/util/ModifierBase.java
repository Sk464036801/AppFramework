package com.appframework.util;

/**
 * 下列代码就是一个简单的示例应用。对于一个java文件来说，它有以下几种modifier：
 * [public, package, protected, private, static, abstract, final]等等。这些modifier中，
 * 有些是可以同时存在的（如：public和static），有些则是互斥的，也就是说只能出现一种（如：public和private）。

	那么我们就可以对这些modifier进行分类，分类的方法就是让它们每一个都占据一个二进制位。下列代码中，
	public占据了低位第一个二进制位，而private则占据了低位第4个二进制位。使用这种分类方法，
	我们就可以很轻松的判断是否包含某一个modifier，也可以判断是否包含一系列modifier。
	如下列代码中的isPublic(int)和hasModifier(int)。

这种用法还可以用在权限管理系统，因为权限系统也会有这种类似的部分可以同时具有，部分必须是互斥的需求。
 * @author gk
 *
 */
public class ModifierBase {
	
	public static int PUBLIC = 0x0001;//1
    public static int PACKAGE = 0x0002; //2
    public static int PROTECTED = 0x0004; //4
    public static int PRIVATE = 0x0008; //8
    public static int STATIC = 0x0010; //16
    public static int ABSTRACT = 0x0020; //32
    public static int FINAL = 0x0040; // 64
    
    public static int MANAGER = 0x0040; // 64
    public static int CITY= 0x0080; // 128
    public static int PROVINCE = 0x0100; // 256
    public static int SYSTEM = 0x0200; // 512
    public static int SUPER_SYSTEM = 0x400; //1024
    
    public static int CITY_MANAGER = MANAGER | CITY;
    public static int PROVINCE_MANAGER = MANAGER | PROVINCE;
    public static int SYSTEM_MANAGER = MANAGER | SYSTEM;
    
    private int modifier = 0;
    
    public ModifierBase(int modifier) {
        this.modifier = modifier;
    }
    
    public boolean isPublic() {
        return (this.modifier & PUBLIC) != 0;
    }
    
    public boolean isPackage() {
        return (this.modifier & PACKAGE) != 0;
    }
    
    public boolean isProtected() {
        return (this.modifier & PROTECTED) != 0;
    }
    
    public boolean isPrivate() {
        return (this.modifier & PRIVATE) != 0;
    }
    
    public boolean hasModifier(int modifier) {
        return (this.modifier & modifier) == modifier;
    }
    
    public boolean hasnotModifier(int modifier) {
        return (this.modifier & modifier) == 0;
    }
    
    public static void main(String[] args) {
        int modifiers1 = PUBLIC | PROTECTED;
        int modifiers2 = PUBLIC | PROTECTED | PRIVATE;
        
        System.out.println("city manager = " + CITY_MANAGER);
        System.out.println("province manager = " + PROVINCE_MANAGER);
        System.out.println("system manager = " + SYSTEM_MANAGER);
        
        ModifierBase base = new ModifierBase(modifiers2); // base is [PUBLIC | PROTECTED | PRIVATE]
        System.out.println(base.hasModifier(modifiers1)); // true because base includes [PUBLIC | PROTECTED]
        ModifierBase base2 = new ModifierBase(modifiers1); // base is [PUBLIC | PROTECTED]
        System.out.println(base2.hasModifier(modifiers2)); // fasle because base2 donot includes [PRIVATE] 
    }

}
