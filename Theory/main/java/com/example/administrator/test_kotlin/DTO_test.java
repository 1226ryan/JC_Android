package com.example.administrator.test_kotlin;

import java.io.Serializable;

/**                 DTO(Data Transfer Object)
 * VO(Value Object)랑 혼용해서 사용.
 * 계층간 데이터 교환을 위한 자바빈즈(Java Beans).
 * Data -> Object 로 변환하는 객체.
 * DTO에서 Obejct는 우리가 만드는 DTO Class.
 *
 * ______________________________________________________________________________________
 * 데이터가 포함된 객체를 한 시스템에서 다른 시스템으로 전달하는 작업을 처리하는 객체이다.
 *
 * ______________________________________________________________________________________
 * 이 객체는 데이터베이스 레코드의 데이터를 매핑하기 위한 데이터 객체를 말한다.
 * DTO 는 보통 로직을 가지고 있지 않고 data 와 그 data 에 접근을 위한 getter, setter 만 가지고 있다.
 * 따라서, Database 에서 Data 를 얻어 Service 나 Controller 등으로 보낼 때 사용하는 객체를 말한다.
 * DTO 는 "데이터를 주고받을 포맷" 이라고 할 수 있다. DTO 객체가 "Serializable" 을 상속한 이유는 Serializable 로 두면 데이터 전송 시 "조각단위로 보내지지 않고 하나의 객체"로 보낼 수 있게 돼서 효과적이다.
 */
public class DTO_test implements Serializable{
    private String name;
    private int value;
    private String data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
/**
 * 위 class 는 name, age 필드에 데이터를 쓰고/읽는 역할인 setter/getter 가 존재.
 * setter/getter 에서 set 과 get 이후에 나오는 단어(또는 단어들)가 property 라고 약속.
 * 윗 클래스에서의 프로퍼티는 name 과 age.
 *
 * 중요한 점은 "프로퍼티"는 멤버변수 name,age 로 결정되는 것이 아닌 "getter/setter 에서의 name 과 age" 임을 명심.
 * (멤버변수의 변수명은 아무렇게나 지어도 영향이 없고 setter/getter 로 프로퍼티를 표현 한다는 의미)
 *
 * Layer간(쉽게 한가지 예를 들자면, 서버 코딩 -> view 코딩)에 데이터를 넘길때는 DTO를 쓰면 편함.
 * form 에서 name 필드 값을 프로퍼티에 맞춰서 값을 다른 페이지로 넘겼을 시, 값을 받아야할 페이지에서는 값을 하나씩 일일이 받는 것이 아니라
 * name속성의 이름이랑 매칭되는 프로퍼티에 자동적으로 DTO가 인스턴스화 되어 PersonDTO를 자료형으로 값을 받을 수 있다는 것.
 *
 * 결론적으로, key & value로 존재하는 데이터는 자동화 처리된 DTO로 변환되여 우리는 손 쉽게 데이터가 셋팅된 오브젝트를 받을 수 있다.
 */


/**                 VO(Value Object)
 * 값 오브젝트는 말 그대로 값을 위해 쓰는 것.
 * 자바는 값 타입을 표현하기 위해 불변 클래스를 만들어 사용한다.
 * 불변 클래스라 하면, readOnly 특징을 가진다. 예를 들자면 String,Integer,Color 클래스등이 있다.이러한 클래스는 중간에 값을 바꿀 수 없고 새로 만들어야 한다.
 * (Color class 를 예로 들면, Red 를 표현하기 위해선 Color.RED 등과 같이 값을 표현하기 위해 getter 기능만이 존재)
 *
 * ______________________________________________________________________________________
 *  DB 혹은 어떠한 물체 틀(형상화)을 잡고 이 틀을 잡기 위한 변수들을 모아둔 것.
 *  DTO 와 동일한 개념이나 차이점은 read only 속성.
 *
 */

/**                 두 가지를 혼용해서 쓰는 이유
 * 직접 데이터를 넣어주기 보다는 넣어진 데이터를 getter를 통해 사용하므로 주 목적은 같다. 하지만 앞서 설명했드시, DTO는 불변 클래스 성격과는 거리가 멀다. 또한, DTO는 인스턴스 개념이고 VO는 리터럴 값 개념.
 *
 * 참고
 * http://mommoo.tistory.com/61
 * http://sumin172.tistory.com/107
 * http://lazymankook.tistory.com/30
 *
 */


