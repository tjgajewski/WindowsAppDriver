// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package system;

@net.sf.jni4net.attributes.ClrType
public class Attribute extends Object {
    
    //<generated-proxy>
    private static Type staticType;
    
    protected Attribute(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    protected Attribute() {
            super(((net.sf.jni4net.inj.INJEnv)(null)), 0);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/UInt32;)V")
    public native void GetTypeInfoCount(net.sf.jni4net.Out<Integer> pcTInfo);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/UInt32;LSystem/UInt32;LSystem/IntPtr;)V")
    public native void GetTypeInfo(int iTInfo, int lcid, long ppTInfo);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Guid;LSystem/IntPtr;LSystem/UInt32;LSystem/UInt32;LSystem/IntPtr;)V")
    public native void GetIDsOfNames(net.sf.jni4net.Ref<Guid> riid, long rgszNames, int cNames, int lcid, long rgDispId);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/UInt32;LSystem/Guid;LSystem/UInt32;SLSystem/IntPtr;LSystem/IntPtr;LSystem/IntPtr;LSystem/IntPtr;)V")
    public native void Invoke(int dispIdMember, net.sf.jni4net.Ref<Guid> riid, int lcid, short wFlags, long pDispParams, long pVarResult, long pExcepInfo, long puArgErr);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;LSystem/Type;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.MemberInfo element, Type type);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;LSystem/Type;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.MemberInfo element, Type type, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.MemberInfo element);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.MemberInfo element, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;LSystem/Type;)Z")
    public native static boolean IsDefined(system.reflection.MemberInfo element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;LSystem/Type;Z)Z")
    public native static boolean IsDefined(system.reflection.MemberInfo element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;LSystem/Type;)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(system.reflection.MemberInfo element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/MemberInfo;LSystem/Type;Z)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(system.reflection.MemberInfo element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.ParameterInfo element);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;LSystem/Type;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.ParameterInfo element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;LSystem/Type;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.ParameterInfo element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.ParameterInfo element, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;LSystem/Type;)Z")
    public native static boolean IsDefined(system.reflection.ParameterInfo element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;LSystem/Type;Z)Z")
    public native static boolean IsDefined(system.reflection.ParameterInfo element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;LSystem/Type;)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(system.reflection.ParameterInfo element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/ParameterInfo;LSystem/Type;Z)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(system.reflection.ParameterInfo element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;LSystem/Type;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(Object element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(Object element);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(Object element, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;LSystem/Type;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(Object element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;LSystem/Type;)Z")
    public native static boolean IsDefined(Object element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;LSystem/Type;Z)Z")
    public native static boolean IsDefined(Object element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;LSystem/Type;)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(Object element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Module;LSystem/Type;Z)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(Object element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;LSystem/Type;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.Assembly element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;LSystem/Type;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.Assembly element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.Assembly element);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;Z)[LSystem/Attribute;")
    public native static Attribute[] GetCustomAttributes(system.reflection.Assembly element, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;LSystem/Type;)Z")
    public native static boolean IsDefined(system.reflection.Assembly element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;LSystem/Type;Z)Z")
    public native static boolean IsDefined(system.reflection.Assembly element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;LSystem/Type;)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(system.reflection.Assembly element, Type attributeType);
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Reflection/Assembly;LSystem/Type;Z)LSystem/Attribute;")
    public native static Attribute GetCustomAttribute(system.reflection.Assembly element, Type attributeType, boolean inherit);
    
    @net.sf.jni4net.attributes.ClrMethod("()LSystem/Object;")
    public native Object getTypeId();
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/Object;)Z")
    public native boolean Match(Object obj);
    
    @net.sf.jni4net.attributes.ClrMethod("()Z")
    public native boolean IsDefaultAttribute();
    
    public static Type typeof() {
        return Attribute.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, Type staticType) {
        Attribute.staticType = staticType;
    }
    //</generated-proxy>
}