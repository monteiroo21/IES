# NMEC-114547

# LAB 4

## 4.1 First Dummy App

### Creating a React App

```
npx create-react-app my-app
cd my-app
npm start
```

- Note: I need to have Node.js installed for this (link on the pratical guide)


React Notes:

    - Components are JavaScript functions that return markup;
    - They must always start with a capital letter (HTML tags must be lowercase);
    - export default -> main component in the file;
    - To return multiple JSX tags we have to wrap them into a shared parent, like <>...</>;
    - To write the CSS rules -> .<className>;
    - If statements are possible;
    - useState -> gives me the current state and a function to update that state;
    

Creating a production build:

```
npm run build
npm install -g serve
serve -s build
```



### 4.2 Props and States

Props are the information that you pass to a JSX tag. For example, className and src are some of the props existent.
It is possible to specify a default value for a prop. The default value is only used if that prop is missing or if you pass propName={undefined}. But if you pass propName={null} or propName={0}, the default value will not be used.

### State

```
const [x, setX] = useState(0);
```
This allows to create the variable x, have a function that changes the value of that variable and define a starting value for that same variable (0)

Although objects in React state are technically mutable, you should always replace them.

```
npm install use-immer
import { useImmer } from 'use-immer'
```

Immer allows us to access objects that are deeply nested, without mutating the object, but instead replacing it, like here:

```
updatePerson(draft => {
  draft.artwork.city = 'Lagos';
});
```

### 4.3 Consume REST API services
Two options:

Fetch API and Axios

GET METHOD with Fecth API:

```
   useEffect(() => {
      const fetchPost = async () => {
         const response = await fetch(
            'https://jsonplaceholder.typicode.com/posts?_limit=10'
         );
         const data = await response.json();
         console.log(data);
         setPosts(data);
      };
      fetchPost();
   }, []);
```


GET METHOD with Axios:

Run the following comand beforehand: npm install axios
```
import axios from "axios";

const client = axios.create({
   baseURL: "https://jsonplaceholder.typicode.com/posts" 
});

(...)

useEffect(() => {
   client.get('?_limit=10').then((response) => {
      setPosts(response.data);
   });
}, []);

```


Axios simplifies the logic to send asynchronous HTTP requests to REST endpoints.

