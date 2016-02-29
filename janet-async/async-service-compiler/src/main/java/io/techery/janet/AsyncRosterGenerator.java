package io.techery.janet;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Nullable;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import io.techery.janet.compiler.utils.Generator;

public class AsyncRosterGenerator extends Generator<AsyncActionClass> {

    protected AsyncRosterGenerator(Filer filer) {
        super(filer);
    }

    @Override public void generate(ArrayList<AsyncActionClass> actionClasses) {
        //create header
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(AsyncActionService.ROSTER_CLASS_SIMPLE_NAME)
                .addModifiers(Modifier.FINAL)
                .addJavadoc("Janet compile time, autogenerated class")
                .superclass(AsyncActionsRosterBase.class);

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();

        Collections.sort(actionClasses, new Comparator<AsyncActionClass>() {
            @Override public int compare(@Nullable AsyncActionClass left, @Nullable AsyncActionClass right) {
                return left.getEvent().compareTo(right.getEvent());
            }
        });
        String event = null;
        for (AsyncActionClass actionClass : actionClasses) {
            if (!actionClass.getEvent().equals(event)) {
                event = actionClass.getEvent();
                constructorBuilder.addStatement("map.put($S, new $T())", event, ParameterizedTypeName.get(ArrayList.class, Class.class));
            }
            constructorBuilder.addStatement("map.get($S).add($T.class)", event, actionClass.getTypeElement());
        }
        classBuilder.addMethod(constructorBuilder.build());
        saveClass(AsyncActionService.class.getPackage().getName(), classBuilder.build());
    }

}