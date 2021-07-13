package com.example.demo.optionaltest;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author zhaoyu
 * @date 2020/1/2
 */
public class OptionalTest {

    public static void main(String[] args) throws Exception{
        PersonModel personModel = new PersonModel();

        //对象为空则打出 -
        Optional<Object> o = Optional.of(personModel);
        System.out.println("1111" + (o.isPresent() ? o.get() : "-"));

        //名称为空则打出 -
        Optional<String> name = Optional.ofNullable(personModel.getName());
        System.out.println("2222" + (name.isPresent() ? name.get() : "-"));

        //如果不为空，则打出xxx
        Optional.ofNullable("test").ifPresent(na -> {
            System.out.println(na + "ifPresent");
        });

        //如果空，则返回指定字符串
        System.out.println("3333: " + Optional.ofNullable(null).orElse("-"));
        System.out.println("4444: " + Optional.ofNullable("1").orElse("-"));

        //如果空，则返回 指定方法，或者代码
        System.out.println(Optional.ofNullable(null).orElseGet(() -> {
            return "5555: hahah";
        }));
        System.out.println(Optional.ofNullable("1").orElseGet(() -> {
            return "6666: hahah";
        }));

        //如果空，则可以抛出异常
//        System.out.println(Optional.ofNullable("1").orElseThrow(() -> {
//            throw new RuntimeException("ss");
//        }));


//        Objects.requireNonNull(null,"is null");


        //利用 Optional 进行多级判断
        EarthModel earthModel1 = new EarthModel();
        //old
        if (earthModel1 != null) {
            if (earthModel1.getTea() != null) {
                //...
                System.out.println("77777");
            }
        }
        //new
        boolean present = Optional.ofNullable(earthModel1)
                .map(EarthModel::getTea)
                .map(TeaModel::getType)
                .isPresent();

        System.out.println("8888： " + present);

        String s = Optional.ofNullable(earthModel1)
                .map(EarthModel::getTea)
                .map(TeaModel::getType)
                .orElseGet(() -> {
                    return  "is null";
                });
        System.out.println(s);


//        Optional<EarthModel> earthModel = Optional.ofNullable(new EarthModel());
//        Optional<List<PersonModel>> personModels = earthModel.map(EarthModel::getPersonModels);
//        Optional<Stream<String>> stringStream = personModels.map(per -> per.stream().map(PersonModel::getName));


        //判断对象中的list
        Optional.ofNullable(new EarthModel())
                .map(EarthModel::getPersonModels)
                .map(pers -> pers
                        .stream()
                        .map(PersonModel::getName)
                        .collect(toList()))
                .ifPresent(per -> System.out.println(per));


//        List<PersonModel> models = Data.getData();
//        Optional.ofNullable(models)
//                .map(per -> per
//                        .stream()
//                        .map(PersonModel::getName)
//                        .collect(toList()))
//                .ifPresent(per -> System.out.println(per));


    }

}
