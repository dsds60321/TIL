# Custom Hooks

## Rules of Hooks
1. Hook 이름은 "use"로 시작해야 합니다: 이 규칙은 사용자 정의 Hook이 잘 보이고, Hook의 특별한 규칙을 따르는지 알 수 있게 합니다. 예를 들어, useCounter 또는 useFetch와 같은 이름을 사용할 수 있습니다.
   1. react내에 코드 컨벤션이므로 지켜야 합니다. 리액트는 use로 정의되어있는 함수는 훅으로 인식하여 찾으며 특별한 규칙을  넣어줍니다. ide 내에서 검증이 됩니다.
2. Hook은 최상위에서만 호출해야 합니다: Hook을 반복문, 조건문, 중첩된 함수 내에서 호출하지 마세요. 이 규칙은 Hook의 호출 순서가 항상 동일하게 유지되어야 한다는 React의 규칙을 준수하는 데 중요합니다.
```javascript
function App() {
  if (condition) const [val,setVal] = useState(0);
}
```
3. Hook은 React 함수 내에서만 호출해야 합니다: 사용자 정의 Hook을 일반 JavaScript 함수에서 호출하지 마세요. 대신, React 함수 컴포넌트 또는 사용자 정의 Hook 내에서 Hook을 호출해야 합니다. **기존 내장 훅(useState, useEffect...) 겹치면 안됩니다.**
* 커스텀 훅에 생성 이유
  1. 로직의 재사용: 컴포넌트 간에 상태 로직을 재사용하기 위해 커스텀 훅을 만듭니다. 이는 중복 코드를 줄이고 코드의 유지 보수를 쉽게 만드는 데 도움이 됩니다. 예를 들어, 여러 컴포넌트에서 데이터를 가져오는 동일한 패치 로직을 가지고 있다면, 이 로직을 useFetch라는 커스텀 훅에 넣어 재사용할 수 있습니다.
  2. 컴포넌트의 분리와 관심사의 분리: 커스텀 훅을 사용하면 컴포넌트의 로직을 분리하고 더 작은, 관리하기 쉬운 부분으로 나눌 수 있습니다. 이는 각 컴포넌트나 훅이 하나의 목적 또는 기능에 집중할 수 있게 하여 코드의 가독성과 유지 보수성을 향상시킵니다.

* customHook을 이용한 상태 관리
  * React 커스텀 훅을 개발할 때, 컴포넌트의 재사용 가능한 로직을 분리하는 것이 목적입니다. 상태(state) 관리는 훅의 중요한 사용 사례 중 하나로써 내부적으로 useState, useReducer 등의 훅을 사용할 수 있습니다.
* **useMyHook: 커스텀 훅 만들기**
  1. 초기 상태 설정: useState를 사용하여 커스텀 훅 내부에 상태를 만들고 초기 값을 설정합니다.
  2. 상태 업데이트 함수: 상태를 갱신할 비즈니스 로직을 함수로 정의합니다.
  3. 상태와 함수 반환: 상태와 상태를 업데이트하는 함수를 반환하여 컴포넌트에서 사용할 수 있도록 합니다.
  4. 커스텀 훅에 상태 변경 : 상태 변경을 공유 하고 싶다면 반환값에 상태 변경 함수를 반환하여 사용하면 된다.
* **useSharedState: 전역적 상태 공유**
  1. 컨텍스트 생성: createContext와 useContext를 통해 전역적으로 상태를 공유할 수 있습니다.
  2. 프로바이더 컴포넌트: 커스텀 훅의 상태와 함수를 프로바이더를 통해 하위 컴포넌트에 전달합니다.
  * 다음은 useState를 사용하여 상태를 관리하는 단순 커스텀 훅의 예시입니다.
```javascript
import { useState } from 'react';

// useCounter라는 이름의 커스텀 훅
function useCounter(initialValue = 0) {
  const [count, setCount] = useState(initialValue);

  const increment = () => setCount(count + 1);
  const decrement = () => setCount(count - 1);
  const reset = () => setCount(initialValue);

  return { count, increment, decrement, reset };
}

// 사용 예: 어떤 컴포넌트에서든 useCounter를 호출
export default function CounterComponent() {
  const { count, increment, decrement, reset } = useCounter();

  return (
    <div>
      <p>Count: {count}</p>
      <button onClick={increment}>증가하기</button>
      <button onClick={decrement}>감소하기</button>
      <button onClick={reset}>리셋</button>
    </div>
  );
}
```
