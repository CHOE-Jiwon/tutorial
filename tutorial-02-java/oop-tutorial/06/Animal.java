public class Animal {
    String name;

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * ERROR: Cannot use this in a static context
 * -> main 함수를 static으로 쓰니까 this 가 먹히질 않는다. 왜?
 * -> this 라는 것은 현재 클래스의 속성을 사용할 때 쓰는건데 static 함수는 그 함수가 이미 메모리에 올라가있단거잖아? 근데 this를 나타내는 클래스는 없어.
 * -> 참고: https://stackoverflow.com/questions/16315488/this-cannot-use-this-in-static-context
 */