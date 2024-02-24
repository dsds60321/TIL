# React Router
## 기본 구성요소
  1. BrowserRouter: 브라우저의 history API를 이용하여 UI와 URL을 동기화하는 컴포넌트입니다.
  2. RouterProvider : createBrowserRouter 함수를 사용하여 생성된 라우터 객체를 앱에 적용할 수 있게 해주는 컴포넌트입니다. 라우터를 배열 형태로 좀 더 선언적으로 구성할 수 있습니다.
  3. Routes: 여러 Route들을 포함하는 컴포넌트로, URL의 경로에 따라 컴포넌트를 렌더링합니다.
  4. Route: 특정 경로와 연결된 컴포넌트를 정의하는 컴포넌트입니다. 특정 경로에 방문했을 때 렌더링할 컴포넌트를 지정합니다.
  5. Link: 사용자가 다른 경로로 이동할 수 있게 하는 앵커 태그(<a>)에 대한 래퍼(wrapper)입니다. 페이지를 새로고침하지 않고 라우팅을 할 수 있게 해줍니다.
  6. NavLink : 네비게이션에 사용하는 Link입니다. activeClassName 또는 activeStyle 등의 속성을 사용하여 현재 선택된 NavLink에 다른 스타일을 적용할 수 있습니다.
* 예시 코드
```javascript
const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    errorElement: <ErrorPage />,
    children: [
      { index: true, element: <Home /> },
      { path: "/products", element: <Products /> },
      { path: "/products/:productId", element: <ProductDetailPage /> },
    ],
  },
]);
function App() {
  return <RouterProvider router={router} />;
}
```
## Outlet
* React Router에서 중첩된 라우트를 구현할 때 사용하는 컴포넌트로, 특정 라우트 아래에 위치한 자식 라우트의 컴포넌트들을 렌더링하는 곳을 지정하는 역할을 합니다.
* 목적 : 부모 라우트의 레이아웃은 유지하면서, 중첩된 자식 라우트의 컴포넌트만 변경하여 렌더링하는 데 사용합니다.
* 사용 방법
  1. Outlet을 자식 컴포넌트를 렌더링할 부모 컴포넌트에 삽입합니다.
  2. 부모 라우트에 해당하는 <Route> 안에 자식 라우트를 중첩시켜 구성합니다
* 예시 코드
```javascript
// App 컴포넌트
import { Routes, Route, Outlet } from 'react-router-dom';

function Layout() {
  return (
    <div>
      <Header />
      {/* 여기에 자식 라우트 컴포넌트가 렌더링됩니다. */}
      <Outlet />
    </div>
  );
}

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="about" element={<AboutPage />} />
        {/* 자식 라우트를 중첩시켜 Layout 내부의 Outlet 위치에 렌더링됩니다. */}
      </Route>
    </Routes>
  );
}

export default App;

```
## Error Page 생성 방법
* react-router-dom을 사용하면 <Route> 컴포넌트로 라우팅을 쉽게 관리할 수 있습니다.
* errorPage가 될 컴포넌트를 만듭니다.
* 아래와 같이 두거나 혹은 root Router에 속성으로 errorElement: <ErrorPage /> 두어서 작업합니다.
```javascript
// NotFoundPage.js
export default function NotFoundPage() {
  return <h2>404 페이지를 찾을 수 없습니다 😢</h2>;
}

// App.js
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import HomePage from './HomePage';
import AboutPage from './AboutPage';
import NotFoundPage from './NotFoundPage';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={HomePage} />
        <Route path="/about" component={AboutPage} />
        {/* 위 경로들과 맞지 않는 모든 요청은 404 페이지로 이동 */}
        <Route component={NotFoundPage} />
      </Switch>
    </Router>
  );
}

export default App;

```
## NavLink 스타일 활용 예제
* NavLink에 end 표시를 함으로써 / 이후 path는 적용되지 않게 하겠다는 선언 할때 사용됩니다.
  * **예) /product** end 표시가 있다면 /product/** <- 모두 에러 페이지에서 제외 됩니다.
```javascript
<NavLink
  to="/"
  className={({ isActive }) =>
    isActive ? classes.active : undefined
  }
  end
>
```

## React Router에 동적 파라미터 받는 방법
* 라우트 설정: Route 컴포넌트를 사용하여 동적 파라미터를 경로에 포함시키는 방법입니다.
  * 예시: <Route path="/profile/:userId" element={<UserProfile />} />
* useParams 훅을 사용하여 라우트 파라미터 값을 불러오는 예제 코드입니다.
```javascript
// UserProfile.js
import React from 'react';
import { useParams } from 'react-router-dom';

const UserProfile = () => {
  // useParams 훅을 사용해 동적 파라미터 값(userId)을 불러옴
  const { userId } = useParams();

  return (
    <div>
      <h1>사용자 프로필</h1>
      <p>{`안녕하세요, 사용자 ${userId}님의 프로필 페이지입니다.`}</p>
    </div>
  );
};

export default UserProfile;

```

* children에 index: true 라면 첫 시작하는 페이지 임을 의미 부모 경로에 "/" 페이지임을 의미 