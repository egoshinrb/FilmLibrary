package com.example.filmlibrary.config.jwt;


//@Component
//public class JWTTokenFilter
//        extends OncePerRequestFilter {
//
//    private final JWTTokenUtil jwtTokenUtil;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    public JWTTokenFilter(JWTTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        //Authorization = Bearer iljhsgi17624fi12.uydvkwqutyd1i7tefcvi12yteci1xt.y2eciy12gtxeci12
//        String token = null;
//        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (header == null || !header.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //Получаем JWT Token
//        //header = Bearer iljhsgi17624fi12.uydvkwqutyd1i7tefcvi12yteci1xt.y2eciy12gtxeci12
//        token = header.split(" ")[1].trim();
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
//
//        //Подтверждение токена
//        if (!jwtTokenUtil.validateToken(token, userDetails)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //уставка пользователя в spring security context (авторизация через токен)
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                userDetails,
//                null,
//                userDetails.getAuthorities()
//        );
//
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(request, response);
//
//
//
//    }
//}

