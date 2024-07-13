package yandex;

import java.util.Set;

/**
 Представим, что в языке совсем нет енума.
 Нужно придумать и сделать один конкретный енум (валюта, цвета, статусы ответов, тд). 
 Минимальные требования:
 - по семантике и удобству использования должен быть максимально похож на родной енум языка (если таковой есть)
 - внутри как можно меньше граблей, которые могут привести к багам при дальнейшем расширении енума
 - список значений задаётся на этапе компиялции, не может быть расширен в рантайме
 - значения безопасно сравниваются по ==
 - значения строго типизированы
 */
// это с собеса яндекса
// p.s. как выяснилось, ожидалось, что я напишу класс со статичными полями, инстансами этого же класса.
class Color {}


