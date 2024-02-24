# REACT
1. swc(speed web compiler)
2. jsx가 무엇인가
3. html 코드는 jsx 코드
4. 애플리케이션에 진입점이 되는 코드 웹사이트가 로딩될때 제일 먼저 실행됨
```javascript
import React from 'react'
import ReactDOM from 'react-dom/client'
import Posts from './Posts'
import './index.css'
// root에 react jsx 코드를 렌더링 하는 코드
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Posts />
    </React.StrictMode>
)
```
5. react에서 컴포넌트에 이름에 처음은 항상 대문자 함수 안에서는 함상 jsx 코드 반환하기
6. JSX 컴포넌트 소문자로 시작한다면 html 코드로 간주 대문자라면 jsx 인식하여 커스텀 컴포넌트 함수를 실행합니다.
7. {} 중괄호로 함수에 자바스크립트 코드를 넣을수 있습니다.
```javascript
const chosenName = Math.random() > 0.5 ? names[0] : names[1];
  return (
    <div>
      <p>{chosenName}</p>
```
8. 컴포넌트 재사용
    1. 동일한 컴포넌트를 여러개 사용한다면 함수가 컴포넌트 별로 실행된다.
    2. 하나에 컴포넌트 혹은 빌트인 컴포넌트들을 여러번 재사용하려면 상위 컴포넌트가 감싸고 있어야합니다. jsx가 반환되는 요소는 단 하나여야합니다.
        1. 어떠한 태그든 형제 요소가 2개 이상 있을수 없다. 있을 경우 최상위 태그가 존재해야한다.
    3. 시작과 종료 태그가 비어있는 값을 사용해도 상관없습니다. <></>
9. 컴포넌트간 속성 전달
    1. props : 전달받은 인자에 객체 형태로 되어 있으며 넣은 값이 속성으로 들어가 있습니다.
```javascript
function Posts() {
   return (
           <main>
              <Post author={"Maximilian"} body={"React.js is awesome!"} />
              <Post author={"Manuel"} body={"Check out the full course!"} />
           </main>
   );
}

function Post(props) {
   return (
           <div>
              <p>{props.author}</p>
              <p>{props.body}</p>
           </div>
   );
}
```

10. react style 추가하기
    1. 하이픈은 제거하고 카멜케이스로 사용 {{ 중괄호 두겹으로 감싸며 자바스크립트 코드를 안에 작성하여 css 작성이 가능합니다.
```javascript
<div style={{color: 'red', txtAlign: 'left'}}
```
2. classes 을 설정해 객체 속성에 접근해 클래스 이름을 받는다.
```javascript
import classes from "./Post.module.css";

function Post(props) {
  return (
    <div className={classes.post}>
      <p className={classes.author}>{props.author}</p>
      <p className={classes.text}>{props.body}</p>
    </div>
  );
}

export default Post;

```
* html에 for -> htmlfor 대신 사용한다.

### 상태
* JSX 코드는 처음 컴포넌트 함수들만 가져가서 실행하기에 특정 이벤트가 발생햇을때 컴포넌트를 재 실행하여 ui를 보여주지 않는다.
* 그러므로 아래와 같은 코드는 동작하지 않는다.
* useState라는 리액트 훅 등을 사용해서 표기한다.
    * 리액트 훅등은 컴포넌트 함수 안에서만 사용해야합니다.
        * useState('기본값'); 반환은 배열을 반환하며 첫번째 인덱스는 현재값 두번째 인덱스는 현재값에 업데이트 함수를 반환합니다.
          <br>값을 변경한다면 새 상태 값을 저장하는것뿐만 아니라 리액트가 이전 스냅샷과 비교해 다르다면 ui를 업데이트 한다.
```javascript

const [enteredBody, setEnteredBody] = useState("");
function changeBodyHandler(event) {
   setEnteredBody(event.target.value);
}

<textarea id="body" required rows={3} onChange={changeBodyHandler} />
<p>{enteredBody}</p>
```
* 샹태 올리기 props 객체는 리액트가 자동으로 컴포넌트로 넘겨줍니다.
* children 리액트 props에 속성을 통해 랩퍼 컴포넌트가 자식 컴포넌트를 감싸는 영역을 지정할수 있다.
* Modal 태그가 NewPost를 감싸고 props.children속성으로 들어갈 컴포넌트에 위치를 표시하여 영역을 잡아줄수 있다.
```javascript
<Modal>
   <NewPost
           onBodyChange={bodyChangeHandler}
           onAuthorChange={authorChangeHandler}
   />
</Modal>

function Modal({ children }) {
   return (
           <>
              <div className={classes.backdrop} />
              <dialog open className={classes.modal}>
                 {children}
              </dialog>
           </>
   );
}
```

```javascript
{modalIsVisible ? (
  <Modal onClose={hideModalHandler}>
    <NewPost
      onBodyChange={bodyChangeHandler}
      onAuthorChange={authorChangeHandler}
    />
  </Modal>
) : null}
```

```javascript
let modalContent;

if (modalIsVisible) {
  modalContent = (
    <Modal onClose={hideModalHandler}>
      <NewPost
        onBodyChange={bodyChangeHandler}
        onAuthorChange={authorChangeHandler}
      />
    </Modal>
  );
}

return (
  <>
    {modalContent}
    <ul className={classes.posts}>
      <Post author={enteredAuthor} body={enteredBody} />
      <Post author={enteredAuthor} body={enteredBody} />
    </ul>
  </>
);
```
* null은 react에서 렌더링 되지 않는다.

* 이전 데이터를 갱신해야하는경우 바로 set함수를 이용해 상태를 변경하는 코드는 좋지 않다 리액트에 상태 갱신은 곧바로 진행되는게 아니기 때문에
  <br>화살표 함수를 통해 곧바로 실행되게 하여야 한다. 화살표 함수 인자에는 이전 데이터가 들어가 있다.
```javascript
function addPostHandler(postData) {
// setPosts([postData, ...posts]); 이전 데이터를 갱신할떄 사용하는건 비추
setPosts((existingPost) => [postData, ...existingPost]);
}
```
* 데이터를 jsx코드로 변경해서 컴포넌트를 생성하여 사용할 수 있다..
```javascript
{posts.length > 0 && (
  <ul className={classes.posts}>
    {posts.map((post) => (
      <Post key={post.body} author={post.author} body={post.body}></Post>
    ))}
  </ul>
)}
```
* React에서는 jsx 코드를 반환해야하기에 다른 프레임워크를 사용하기 전에는 await 같은 promise를 반환할 수 없다
* React에서 응답값을 api로 받아오고 상태를 변경하는 코드같은 경우에 새로운 함수 호출 상태변경 컴포넌트 변경 각 변경이 이뤄질때마다
<br> 리액트 컴포넌트가 변경되므로 무한루프가 발생될수 있어 부수 효과를 제어하기위해 리액트에는 useEffect()라는 훅을 제공한다.
## useEffect
* useEffect는 React의 Hook 중 하나로, 함수 컴포넌트 내에서 side effect없이 사용할때 사용합니다.
  * fetching, 구독 설정, 수동적인 DOM 조작 등 다른 컴포넌트에 영향을 미치거나, 비동기 작업을 수행할때 주로 사용합니다.
  * useEffect에 전달되는 함수는 컴포넌트가 렌더링된 후에 호출됩니다. 따라서, 이 함수 내에서 DOM 업데이트를 수행하거나, 네트워크 요청을 보내는 등의 작업을 수행할 수 있습니다.<br>
또한, useEffect는 두 번째 인자로 배열을 받을 수 있습니다. 이 배열에는 effect가 의존하는 값들을 명시할 수 있습니다. 이 배열의 값이 변경될 때만 effect가 실행됩니다. 예를 들어, 아래와 같이 사용할 수 있습니다.
```javascript
useEffect(() => {
  // 여기에 side effect를 수행하는 코드를 작성합니다.
}, [dependency]); // dependency가 변경될 때만 이 effect가 실행됩니다.

```
* useEffect의 두 번째 인자로 빈 배열을 전달하면, effect는 컴포넌트가 마운트될 때 한 번만 실행되고, 컴포넌트가 언마운트될 때 정리 작업을 수행합니다.
```javascript
useEffect(() => {
  // 컴포넌트가 마운트될 때 수행됩니다.

  return () => {
    // 컴포넌트가 언마운트될 때 수행됩니다.
  };
}, []); // 빈 배열을 전달하면 effect는 한 번만 실행됩니다.
```

## Routing
### 1. react-router-dom 라이브러리 설치
### 2. 주요 컴포넌트 사용법
   1. BrowserRouter: HTML5 history API를 사용하는 라우터로 URL을 깔끔하게 보여줍니다.
   2. Route: 애플리케이션의 특정 경로에 컴포넌트를 매핑합니다.
   3. Link: 애플리케이션 내의 다른 경로로 이동할 수 있는 링크를 생성합니다.
   4. NavLink: 현재 경로와 일치할 때 특별한 스타일링을 적용할 수 있는 Link의 특별 버전입니다.
   5. Switch: 여러 Route 중에서 첫 번째로 일치하는 Route만 렌더링하는 컴포넌트입니다. (주의: v5 이후부터는 Routes로 대체)
   6. Redirect: 특정 경로에 접근하려 할 때, 다른 경로로 리디렉션합니다.
```javascript
import { RouterProvider } from "react-router-dom";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider />
  </React.StrictMode>,
);
```
### 3. RouterProvider 사용법과 LayoutRouter 설명
* RouterProvider 및 LayoutRouter는 React Router DOM에서 고급 라우팅 기능을 제공합니다.
* **RouterProvider 사용법**
  * RouterProvider는 React Router v6에서 라우팅 시스템을 구성하기 위한 새로운 인터페이스입니다.
  1. createBrowserRouter: 라우팅 정보를 가진 객체를 생성합니다. RouterProvider에 전달될 브라우저 라우터의 인스턴스를 만들어주는 함수입니다.
  2. routes: 라우트 구성에 대한 배열로, 어플리케이션의 라우트 구조를 정의합니다. 일반적으로 객체의 배열로, 경로(path), 요소(element), 로더(loader), 액션(action) 등을 포함할 수 있습니다.
  3. basename: 모든 라우트의 베이스 URL을 지정하는 문자열입니다.
  4. loader : 특정 라우트에 대한 데이터 의존성을 해결하기 위해 사용됩니다. 라우팅을 처리할 때 해당 라우트에 대한 데이터를 미리 로드할 수 있는 기능을 제공합니다.
  5. action : action 속성은 사용자가 폼을 제출(submit)할 때 호출되는 함수를 정의하는 데 사용됩니다. 이를 통해 폼 데이터를 처리하고 관련 동작을 실행할 수 있습니다.
```javascript
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

const router = createBrowserRouter([
  // 여기에 라우트 구성을 배열로 지정
]);

function App() {
  return (
    <RouterProvider router={router} />
  );
}
```

* **Loader 속성**
* loader 속성은 React Router v6 이상에서 사용되며, 특정 라우트에 대한 데이터 의존성을 해결하기 위해 사용됩니다. 라우팅을 처리할 때 해당 라우트에 대한 데이터를 미리 로드할 수 있는 기능을 제공합니다.
* **Loader 예시**
~~~javascript
import { createBrowserRouter } from "react-router-dom";

// 데이터를 로드하는 함수입니다. 예를 들어 API 호출 등이 있을 수 있습니다.
const rootLoader = async () => {
  // 데이터 로딩 로직
};

// 라우터를 생성하고 경로 및 라우트 컴포넌트를 정의할 수 있습니다.
const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    loader: rootLoader, // 라우트가 활성화될 때 실행될 로더 함수
  },
]);
// =============
// loader 기능이 필요한 컴포넌트에서 동기적인 값이 필요한 함수를 export하여 loader 속성해서 import 하여 주로 사용된다. 
~~~

* **Action과 Form 설명**
* 사용 예시
~~~javascript
// createBrowserRouter내에 속성에 action을 정의해며 해당 컴포넌트에서 함수를 import 받아 사용 가능하다
{ path: "/create-post", element: <NewPost /> , action: newPostAction},

// NewPost 컴포넌트에 있는 newPostAction 함수
export async function action({request}) {
    console.log(request)
    const formData = await request.formData();
    const postData = Object.fromEntries(formData);
    await fetch('http://localhost:8080/posts', {
        method: 'POST',
        body: JSON.stringify(postData),
        headers: {
            'Content-Type' : 'application/json',
        },
    });

    return redirect('/');
}
~~~

* **LayoutRouter 설명**
1. 구현방법
   1. 각기 다른 레이아웃을 위한 컴포넌트를 생성합니다.
   2. 각 레이아웃에서 사용할 <Routes> 및 <Route> 컴포넌트들을 정의하여, 해당 레이아웃을 사용하는 경로들을 정의합니다.
   3. 기본 라우팅 설정에서 /layoutA과 같은 경로에 레이아웃 컴포넌트를 정의하고, 하위 경로를 해당 레이아웃 컴포넌트 내부에 위치한 <Route>로 정의하여, 레이아웃에 따른 라우트를 관리합니다. 
```javascript
import { Routes, Route } from 'react-router-dom';
import LayoutA from './layouts/LayoutA';
import LayoutB from './layouts/LayoutB';
import HomePage from './HomePage';
import UserPage from './UserPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/layoutA/*" element={<LayoutA />}>
        // LayoutA용 경로 정의
      </Route>
      <Route path="/layoutB/*" element={<LayoutB />}>
        // LayoutB용 경로 정의
      </Route>
    </Routes>
  );
}
```

### Outlet
* Outlet은 React Router Dom에서 특정 페이지들이 공통적으로 사용하는 부분에 대해 표시할 때 사용됩니다. 영역을 정의하고 여러 페이지에서 이 영역 안에 다른 컴포넌트의 내용을 렌더링할 수 있도록 합니다.
* **사용 예제**
```javascript
import { Outlet } from 'react-router-dom';

function Layout() {
  return (
    <div>
      <header>헤더 영역</header>
      <main>
        <Outlet /> {/* 이 위치에 Route에 따른 컴포넌트가 렌더링됩니다 */}
      </main>
      <footer>푸터 영역</footer>
    </div>
  );
}

```

* **라우트 설정 예제**
  * Layout 컴포넌트에서 영역을 정의했다면, 라우트 설정에서 이 Layout을 사용해야 합니다.
```javascript
import { Routes, Route } from 'react-router-dom';

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="about" element={<About />} />
        <Route path="profile" element={<Profile />} />
        {/* 이렇게 설정하면 'about', 'profile' 경로의 컴포넌트가 <Layout />내의 <Outlet /> 위치에 렌더링됩니다. */}
      </Route>
    </Routes>
  );
}
```
* **Link 컴포넌트 사용 예제**
* React Router에서 Link 컴포넌트는 사용자가 애플리케이션 내에서 새로운 경로로 네비게이션 할 수 있게 해주는 기본 요소입니다. 
<br>웹 페이지에서 a 태그와 유사하지만, 페이지를 새로고침하지 않고 클라이언트 사이드에서 라우트를 변경할 수 있게 해줍니다
* LINK 컴포넌트 속성
  1. to: 이동하고자 하는 경로를 나타내며, 문자열이나 객체형태로 지정할 수 있습니다.
  2. replace (옵션): true로 설정하면, 현재 엔트리를 새로운 주소로 교체함으로써 브라우저 히스토리에 새로운 기록을 쌓지 않습니다.
  3. innerRef (옵션): Link 요소에 대한 참조를 얻을 수 있게 해줍니다.
```javascript
import { Link } from 'react-router-dom';

function Tab() {
  return (
    <nav>
      <Link to="/">홈</Link>
      <Link to="/about">회사소개</Link>
      <Link to="/contact">연락처</Link>
    </nav>
  );
}
```
* **useNavigate 훅 사용법**
* useNavigate 훅은 React Router 버전 6에서 새롭게 소개됐으며, 애플리케이션 내의 페이지 간 라우팅을 위한 기능을 제공합니다. 
<br>이 훅은 과거 버전에서 쓰였던 useHistory와 useLocation 훅의 업그레이드된 대체 물입니다. 함수 컴포넌트 내에서 프로그래매틱하게 페이지 전환을 하기 위해 사용됩니다.
* useNavigate 훅 기능
  1. 페이지 이동: navigate 함수에 경로를 전달하여 특정 페이지로 이동합니다. 옵션으로 { replace: true }를 사용하면, 히스토리 스택에 기록을 남기지 않고 현재 기록을 대체할 수 있습니다.
  2. 리다이렉션: navigate 함수는 리다이렉션을 구현하는 데에도 사용될 수 있습니다. 사용자가 특정 조건에 해당할 때 자동으로 다른 페이지로 이동시킬 수 있습니다.
  3. 명령형 네비게이션: Link 컴포넌트가 선언적 네비게이션을 제공하는 반면, useNavigate를 사용하면 요구에 따라 코드에서 직접 페이지 이동을 명령할 수 있습니다.

```javascript
import { useNavigate } from 'react-router-dom';

function ExampleComponent() {
  let navigate = useNavigate();

  function handleOnClick() {
    navigate('/homepage', { replace: true });
  }

  return <button onClick={handleOnClick}>홈으로 이동</button>;
}

```

