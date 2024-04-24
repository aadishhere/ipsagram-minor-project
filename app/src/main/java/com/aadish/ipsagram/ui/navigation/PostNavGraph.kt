package com.aadish.ipsagram.ui.navigation

//@Composable
//fun NavGraphBuilder.postNavGraph(navController: NavHostController) {
//    navigation(startDestination = Graph.COMMUNITY, route=BottomBarScreen.Post.route) {
//        composable(Graph.COMMUNITY) {
//            CommunityScreen(navController)
//        }
//        composable(Graph.POST_DETAILS) {navBackStackEntry ->
//            val postID = navBackStackEntry.arguments?.getStr("postID")
//            if (postID != null) {
//                PostDetailsScreen(postID = postID, navController = navController)
//            } else {
//                PostDetailsScreen(postID = 0, navController = navController)
//            }
//        }
//    }
//}

//@Preview
//@Composable
//fun MyNavGraphPreview() {
//    FinalProjectTheme(darkTheme = true) {
//        val navController = rememberNavController()
//
//        NavHost(
//            navController = navController,
//            route = "post_preview",
//            startDestination = BottomBarScreen.Post.route
//        ) {
//            postNavGraph(navController)
//        }
//    }
//}