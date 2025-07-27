# spring-security-demo
Spring Security is a powerful and highly customizable security framework for Java-based applications, particularly those built with the Spring Framework. It focuses on providing comprehensive security solutions, primarily dealing with authentication and authorization.z

Features:

 1. Authentication:

- Verifies user identity (username/password, token, etc.)
- Supports form login, basic auth, OAuth2, JWT, LDAP

 2. Authorization:

- Grants or denies access to resources based on roles/permissions
- Supports method-level (`@PreAuthorize`) and URL-level security
1. Filter:
    - A **filter** in Spring Boot is a component that intercepts HTTP requests and responses to perform pre-processing or post-processing tasks.
    - It is commonly used for logging, security, CORS, or request validation.

## **🎯Ways of Apply Spring Security:-**

1. Apply Basic Auth:(By Dependency)- 
    - Protect the Backend / server code
    - in Basic Auth demand userName and Password which we above done and also can do default
    
    **Note:**- *
    
    - its Only Secure as Layer in backend but if know anybody userName and Password then our all Endpoint in Danger so we need to protect Them .
    - In this Apply protect all endpoint which need to public , Access to Admin  Role,Access of Teacher Role, etc.
    
    ## Apply SpringBoot Security Basic Auth with Default UserName and Password in Fresh Project:-
    
    1. ADD Spring boot Security Dependecy  In Pom.xml
    2. Run the again Springboot Application
    3.  Its block all API endpoint and run get API in browser 
    
    ![image.png](attachment:24a2aa64-8977-414e-b293-25fca2784857:image.png)
    
    1. By Default :- Username:- user and password come in console put it and run.
    2. Its work Now 
    3. This is simple: Apply security in java by Spring Boot. 
    
    ## Apply Manual userName and Passoword in  SpringBoot Security in Fresh Project:-
    
    Write inside in Application.properties
    
    `spring.security.user.name=DemoProject
    spring.security.user.password=dummyProject123`
    
2. Apply Security in API Endpoint:- Apply Security Filter Chain
    
    (1). Before Springboot security 5.6 -
    
    - used extends WebSecurityConfigurerAdapter  which is not recommanded for Production
    
    **Reason(Not Recommanded):-**
    
    - **Encourages Composition** – The new approach uses `@Bean` and avoids inheritance, making code more modular and testable.
    - **Cleaner Syntax** – Lambda-style configuration improves readability and aligns with modern Spring practices.
    - **Better Flexibility** – Easier to customize and integrate with other beans compared to tightly-coupled `WebSecurityConfigurerAdapter`.
    
    (2).SecurityFilterChain(Recommanded / new):-  
    
    ```java
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
    
        @Autowired
        private UserDetailsService userDetailsService;
    
        @Bean
        // Apply for Filter before request reached to Server
       @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/home").permitAll()  // Public endpoints
                .anyRequest().authenticated()                // All others required
            )
            .httpBasic(); // You can replace this with .formLogin() if using form-based login
    
        return http.build();
    }
    
        // ⬇️ Manually define AuthenticationManager
        @Bean
        //  filterChain call AuthenticationManager
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
          //  DaoAuthenticationProvider this is Provider Manager who call authentication Provider
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
    
            return new ProviderManager(provider);
        }
    
        @Bean
        public UserDetailsService userDetailsService() {
            List<UserDetails> users = List.of(
                User.withUsername("john").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("roshni").password(passwordEncoder().encode("abcd")).roles("ADMIN").build()
            );
            return new InMemoryUserDetailsManager(users);
        }
    
        @Bean
        
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
    
    ```
    

Keyword Used in Code :- 

1. SecurityFilterChain:- Apply filter on API Endpoint which API need Authication or which is public where Auth not needed .
2. .requestMatchers:- Is used to match the Url path 
3. . PermitALL:- giving permission as public anybody can access
4. .authenticated:- used for apply must need to authentication 
5. AuthenticationManager:- after filter API Endpoint go to AuthenticationManager for check the requesting Data
6. DaoAuthenticationProvider:- used as Provider Manager which have Authenication Provider
7. InMemoryUserDetailsManager:- used for Store in Tempoary Memory not in DB its used for Testing
8.  PasswordEncoder:- used for Encode the Password

## How Spring Security Works:-

![image.png](attachment:d7c5730e-3b32-454a-95e2-3371739b99e4:image.png)

### **Important Class and Interface In Security:-**

### **1. SecurityFilterChain**

Uses: Entry point where we define which endpoint needs authentication.

**Code**:

```java

http.authorizeHttpRequests(auth -> auth.requestMatchers("/user/**").authenticated());
```

---

### **2. AuthenticationManager**

Uses: Takes username & password, sends to AuthenticationProvider for validation.

**Code**:

```java

Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken("john", "1234"));
```

---

### **3. AuthenticationProvider**

Uses: Receives credentials from AuthenticationManager and passes to UserDetailsService.

**Code**:

```java

authProvider.setUserDetailsService(customUserDetailsService); // connects to next step
```

---

### **4. UserDetails (CustomUserDetails)**

Uses: Loads user data from DB (by username), returns UserDetails to Provider.

**Code**:

```java

return new CustomUserDetails(user); // inside loadUserByUsername("john")
```

### 🔁 Summary (Flow Chain):

```

AuthenticationManager.authenticate()
        ↓
AuthenticationProvider (validate type, call userDetailsService)
        ↓
CustomUserDetailsService.loadUserByUsername(username)
        ↓
Returns CustomUserDetails(user)
        ↓
Provider checks password
        ↓
If valid → returns authenticated object to Manager

```

### Basic  Spring Boot Security filter chain  flow for username and password, either set manually or use the default configuration.

Flow:- 

`Frontend → Filter (e.g.,UsernamePasswordAuthticationFilter)
→ AuthenticationManager → Provider → UserDetailsService → DB
→ If valid → SecurityContext updated → Token/session created
→ (Controller usually not called)`

### Flow In JWT SpringBoot :-

🔹**Case 1: Login Request:-**

`Frontend → Controller (/auth/login)
→ Service → Repo → DB
→ If valid → JWT Token returned`

🔹**Case 2: Protected Routes (after login):-**

`Frontend → Filter (JwtAuthenticationFilter)
→ Extract & validate JWT
→ If token valid → set SecurityContext
→ Pass to Controller → Service → Repo → DB`
