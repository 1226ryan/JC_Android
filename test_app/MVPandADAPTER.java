package com.example.administrator.test_app;

public class MVPandADAPTER {

    /**
     * Adapter
     *
     * 반복적인 형태를 띈 많은 수의 View를 효율적으로 관리하기 위해 재활용 할 수 있는 View로 바꾸고 화면에 필요한 View만 표시할 수 있도록 함으로써 "메모리"와 "동작"에 큰 효율을 가지고 있음
     * 반복적인 View는 특정 Frame으로 정의하고 Data만 관리함으로써 개발자는 Frame과 Data만 정의하면 해당 화면을 매우 쉽게 구현할 수 있음.
     */

    /**
     * MVP
     *
     * View : 실제 View 에 접근하고 화면을 갱신하는 역할을 수행, 사용자의 실질적인 이벤트가 발생하고, 이를 처리 담당자인 Presenter로 전달
     *          중요) 완전한 View의 형태를 가지도록 설계합니다. 계산을 하거나, 데이터를 가져오는 등의 행위는 Presenter에서 처리하도록
     * Presenter : View 로부터 이벤트를 전달받고 View 에서 알 수 없는 Data 에 접근하고 로직을 수행하도록 Model 에 요청함.
     *          View와는 무관한 Data등을 가지고, 이를 가공하고, View에 다시 전달하는 역할
     * Model : Data 에 직접 접근하고 관리함. Presenter 가 요청한 작업을 수행, Data의 전반적인 부분을 담당하고, 네트워크, 로컬 데이터 등을 포함
     */

    /**
     * MVP, MVC 등에서 Model 의 주역할은 데이터에 직접 접근하고 관리하고 외부의 요청을 수행하도록 합니다.
     *
     */
}
