package samaryanin.avitofork.feature.feed.ui.feature.card.components

//@Composable
//fun ScrollableAdditionalCategories() {
//    val categories = listOf(
//        CategoryUI("Авто", R.drawable.car_black),
//        CategoryUI("Недвижимость", R.drawable.service_black),
//        CategoryUI("Услуги", R.drawable.service_black),
//        CategoryUI("Электроника", R.drawable.smartphone_black)
//    )
//
//    LazyRow(modifier = Modifier.fillMaxWidth()) {
//        items(categories) { category ->
//            Row(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .clickable { /* Handle click */ },
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                AsyncImage(
//                    model = category.imageRes,
//                    contentDescription = category.name,
//                    modifier = Modifier
//                        .size(40.dp)
//                        .padding(end = 8.dp),
//                    contentScale = ContentScale.Fit
//                )
//                Text(text = category.name)
//            }
//        }
//    }
//}