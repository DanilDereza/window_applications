package com.example._2

import javafx.animation.FadeTransition
import javafx.animation.TranslateTransition
import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.media.MediaPlayer
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration
import javafx.stage.Screen
import java.nio.file.Paths

enum class ImageStyle {
    CIRCLE, RECTANGLE
}

class Config {
    var alpha = 0.9
    var openTime = 7000.0
    var imageType = ImageStyle.CIRCLE
    var image = "https://cdna.artstation.com/p/assets/images/images/019/533/214/large/marcos-vinicius-gritten-gay-italian-1.jpg?1563913019"
    var soundPath = "C:/Users/danil/Desktop/Window applications/Meow.mp3";
    var PosX = "Right"
    var PosY = "Bottom"
    var title = "Во-оля"
    var titleStyle = "-fx-text-fill: Indigo; -fx-font-size: 18px;"
    var message = "Congratulations"
    var messageStyle = "-fx-text-fill: red; -fx-font-size: 16px;"
    var appName = "AliExpression"
    var appNameStyle = "-fx-text-fill: DarkSlateGray; -fx-font-size: 12px;  -fx-padding: 0 0 0 45;"
    var btn1 = "True"
    var btn1Style = "-fx-background-color: LimeGreen; -fx-text-fill: Yellow; -fx-font-size: 17px"
    var btn2 = "True"
    var btn2Style = "-fx-background-color: FireBrick; -fx-text-fill: Black; -fx-font-size: 17px"
    var cursor = "HAND"
    var TransitionAnimType = "Translate"
}

class Toast {
    private var config = Config()
    private val windows = Stage()
    private var root = BorderPane()
    private var box = HBox()
    private var primaryScreenBounds = Screen.getPrimary().getVisualBounds()

    class Builder {
        private var config = Config()

        fun build(): Toast  {
            var toast = Toast()
            toast.config = config
            toast.build()

            return toast
        }
    }

    private fun build() {
        windows.initStyle(StageStyle.TRANSPARENT)

        windows.scene = Scene(root)
        windows.scene.fill = Color.TRANSPARENT
        windows.sizeToScene()

        root.style = "-fx-background-color: #ffffff; -fx-padding: 10 10 10 10"

        setImage()

        val vbox = VBox()
        val hbox = HBox()


        val title = Label(config.title)
        val message = Label(config.message)
        val appName = Label(config.appName)
        title.style = config.titleStyle
        message.style = config.messageStyle
        appName.style = config.appNameStyle
        vbox.children.addAll(title, message, appName)

        if(config.btn1 == "True") {
            var button1 = Button("Accept")
            button1.style = config.btn1Style
            button1.cursor = Cursor.cursor(config.cursor)
            hbox.children.add(button1)
        }
        if(config.btn2 == "True") {
            var button2 = Button("Close")
            button2.style = config.btn2Style
            button2.cursor = Cursor.cursor(config.cursor)
            hbox.children.add(button2)
        }
        hbox.setSpacing(75.0)
        box.children.add(vbox)
        box.children.add(hbox)
        root.center = box
        root.bottom = hbox
    }

    private fun setImage() {
        if (config.image.isEmpty()) {
            return
        }

        val iconBorder = if (config.imageType == ImageStyle.RECTANGLE) {
            Rectangle(100.0, 100.0)   //width,  height
        }
        else {
            Circle(50.0, 50.0, 50.0)
        }
        iconBorder.setFill(ImagePattern(Image(config.image)))
        box.children.add(iconBorder)
    }

    private fun openAnimation() {
        if (config.PosX == "Left") {
            windows.x = 0.0
        }
        else if (config.PosX == "Right") {
            windows.x = primaryScreenBounds.getWidth() - windows.scene.width
        }

        if (config.PosY == "Top") {
            windows.y = 0.0
        }
        else if (config.PosY == "Bottom") {
            windows.y = primaryScreenBounds.getHeight() - windows.scene.height
        }

        if (config.TransitionAnimType == "Translate") {
            val anim = TranslateTransition(Duration.millis(1500.0), root)

            if (config.PosY == "Bottom" && config.PosX == "Right") {
                anim.setFromY(windows.scene.height)
                anim.setByY(-windows.scene.height)
            } else if (config.PosY == "Bottom" && config.PosX == "Left") {
                anim.setFromX(-windows.scene.width)
                anim.setByX(windows.scene.width)
            } else if (config.PosY == "Top" && config.PosX == "Left") {
                anim.setFromY(-windows.scene.height)
                anim.setByY(windows.scene.height)
            } else if (config.PosY == "Top" && config.PosX == "Right") {
                anim.setFromX(windows.scene.width)
                anim.setByX(-windows.scene.width)
            }
            anim.cycleCount = 1
            anim.play()
        }
        else if(config.TransitionAnimType == "Fade") {
            val anim = FadeTransition(Duration.millis(1500.0), root)
            anim.fromValue = 0.0
            anim.toValue = config.alpha
            anim.cycleCount = 1
            anim.play()
        }
    }

    private fun closeAnimation() {
        if (config.TransitionAnimType == "Translate") {
            val anim = TranslateTransition(Duration.millis(1500.0), root)


            if (config.PosY == "Bottom" && config.PosX == "Right") {
                anim.setByY(windows.scene.height)
            } else if (config.PosY == "Bottom" && config.PosX == "Left") {
                anim.setByX(-windows.scene.width)
            } else if (config.PosY == "Top" && config.PosX == "Left") {
                anim.setByY(-windows.scene.height)
            } else if (config.PosY == "Top" && config.PosX == "Right") {
                anim.setByX(windows.scene.width)
            }

            anim.cycleCount = 1
            anim.onFinished = EventHandler {
                Platform.exit()
                System.exit(0)
            }
            anim.play()
        }
        else if (config.TransitionAnimType == "Fade") {
            val anim = FadeTransition(Duration.millis(1500.0), root)
            anim.fromValue = config.alpha
            anim.toValue = 0.0
            anim.cycleCount = 1
            anim.onFinished = EventHandler {
                Platform.exit()
                System.exit(0)
            }
            anim.play()
        }
    }

    private fun music() {
        if(config.soundPath.isEmpty()){
            return
        }
        val h =  Media(Paths.get(config.soundPath).toUri().toString());
        val mediaPlayer = MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setStopTime(Duration(7000.0))
        mediaPlayer.setVolume(0.35)
    }

    fun start() {
        windows.show()
        openAnimation();
        music();
        val thread = Thread {
            try {
                Thread.sleep(config.openTime.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            closeAnimation()
        }
        Thread(thread).start()
    }

}


class SomeClass: Application() {
    override fun start(p0: Stage?) {
        var toast = Toast.Builder().build()
        toast.start()
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SomeClass::class.java)
        }
    }
}
