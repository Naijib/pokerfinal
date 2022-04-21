import com.thepoker.Connection
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import java.util.*

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        val connections = Collections.synchronizedSet<Connection>(LinkedHashSet())
        var summ = 0
        var moy = 0
        webSocket("/chat") {
            println("Adding user!")
            val thisConnection = Connection(this)
            connections += thisConnection
            try {
                //send("You are connected! There are ${connections.count()} users here.")
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    thisConnection.choiceNumber = receivedText
                    if (receivedText == "resultat"){
                        connections.forEach {
                            it.session.send("Voici le résultat des votes : " + moy)
                        }
                    }
                    else {
                        summ += thisConnection.choiceNumber.toInt()
                        moy = summ / connections.count()
                        // textWithUsername = "[${thisConnection.name}]: $receivedText"
                        //connections.forEach {
                            //it.session.send("" + moy)
                        //}
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $thisConnection!")
                connections -= thisConnection
                summ -= Integer.parseInt(thisConnection.choiceNumber)
            }
        }
    }
}
