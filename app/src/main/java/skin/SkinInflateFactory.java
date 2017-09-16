package skin;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MyPC on 2017/3/17.
 * <p>
 * 监听一个控件，页面的创建过程
 */

public class SkinInflateFactory implements LayoutInflaterFactory {
    //系统的控件补全，如TextView Button 等
    private final String[] prefixList = new String[]{
            "android.widget.",
            "android.view.",
            "android.webkit."
            //下面的都是系统控件但是补全的
            /*"android.support.design.widget.",
            "android.support.v7.widget.",
            "android.support.v4.view.",
            "android.support.v4.widget."*/
    };

    /**
     * 缓存整个页面需要换肤的view
     */
    private Map<View, SkinItem> skinItems = new HashMap<>();
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //1.获取view
        View view = null;
        if (name.contains(".")) {
            //自定义控件(是全类名）
            view = createView(context, attrs, name);
        } else {
            //系统控件（非全类名）
            for (String s : prefixList) {
                view = createView(context, attrs, s + name);
                if (view != null) {
                    break;
                }
            }
        }

        //2.刷选需要换肤的控件，并换肤
        if (view != null) {
            parseSkinViewWidget(view, context, attrs);
        }

        return view;
    }

    /**
     * 提供给外界的给整个也面换肤的方法
     */
    public void startChangeSkins() {
        Set<Map.Entry<View, SkinItem>> entries = skinItems.entrySet();
        for (Map.Entry<View, SkinItem> entry : entries) {
            View view = entry.getKey();
            SkinItem skinItem = entry.getValue();
            //给控件换肤操作（实际上就是遍历每个需要换肤的属性）
            skinItem.apply();
        }
    }


    private void parseSkinViewWidget(View view, Context context, AttributeSet attrs) {

        //这里集合实际上保存了一个控件里面的需要换肤的多个属性，如TextView里面可以设置，background,textcolor,texsize那么这些属性就被被保存了
        List<AbsSkinInterface> skinintefaces = new ArrayList<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //background
            String attributeName = attrs.getAttributeName(i);

            //  @drawable/ic_lunch
            String attributeValue = attrs.getAttributeValue(i);

            int id = -1;

            //类型名，如:color drawable
            String attributeValueType = "";

            //入口名 ic_lunch,selector_text_color
            String attributeValueName = "";

            AbsSkinInterface skinInterface = null;
            if ("background".equals(attributeName)) {
                //@color/colorPrimary=@21231212;
                id = Integer.parseInt(attributeValue.substring(1));//21231212

                // 入口名字，例如 colorPrimary
                attributeValueName = context.getResources().getResourceEntryName(id);

                // 入口类型，例如 color
                attributeValueType = context.getResources().getResourceTypeName(id);

                //textColor="@color/colorPrimary"
                skinInterface = new BackgroundSkin(attributeName, attributeValueType,
                        attributeValueName, id);

            } else if ("textColor".equals(attributeName)) {
                //@color/colorPrimary=@21231212;
                id = Integer.parseInt(attributeValue.substring(1));//21231212

                // 入口名字，例如 colorPrimary
                attributeValueName = context.getResources().getResourceEntryName(id);

                // 入口类型，例如 color
                attributeValueType = context.getResources().getResourceTypeName(id);

                //textColor="@color/colorPrimary"
                skinInterface = new TextColorSkin(attributeName, attributeValueType,
                        attributeValueName, id);

            } else if ("textSize".equals(attributeName)) {

            }else if("pstsIndicatorColor".equals(attributeName)){
                //@color/colorPrimary=@21231212;
                id = Integer.parseInt(attributeValue.substring(1));//21231212

                // 入口名字，例如 colorPrimary
                attributeValueName = context.getResources().getResourceEntryName(id);

                // 入口类型，例如 color
                attributeValueType = context.getResources().getResourceTypeName(id);

                //textColor="@color/colorPrimary"
                skinInterface = new PstsIndicatorColor(attributeName, attributeValueType,
                        attributeValueName, id);
            }else if("pstsSelectedTabTextColor".equals(attributeName)){
                //@color/colorPrimary=@21231212;
                id = Integer.parseInt(attributeValue.substring(1));//21231212

                // 入口名字，例如 colorPrimary
                attributeValueName = context.getResources().getResourceEntryName(id);

                // 入口类型，例如 color
                attributeValueType = context.getResources().getResourceTypeName(id);

                //textColor="@color/colorPrimary"
                skinInterface = new PstsSelectedTabTextColor(attributeName, attributeValueType,
                        attributeValueName, id);
            }

            if (skinInterface != null) {
                skinintefaces.add(skinInterface);
            }
        }
        //封装一个需要换肤的控件
        SkinItem skinItem = new SkinItem(view, skinintefaces);
        //把每一个需要换肤的控件保存
        skinItems.put(view, skinItem);
        //直接在监听View的创建的时候设置 样式
        skinItem.apply();


    }

    /**
     * 创建view
     *
     * @param context
     * @param attrs
     * @param name    android.widget.TextView
     * @return
     */
    private View createView(Context context, AttributeSet attrs, String name) {
        try {
            Class clazz = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = clazz.getConstructor(new Class[]{Context
                    .class, AttributeSet.class});
            constructor.setAccessible(true);

            return constructor.newInstance(context, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
