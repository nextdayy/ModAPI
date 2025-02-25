# Hypixel Mod API

The Hypixel Mod API is an implementation of custom packets for communicating with the Hypixel Server via plugin messages.

Currently, the API is in an early preview state to gather community feedback. The API is subject to change and may be 
altered or disabled at any time. You can read more about on 
the [announcement forum thread](https://hypixel.net/threads/hypixel-mod-api-developer-preview-feedback.5635119/).


## Mod Distributions

Official downloads of the Hypixel Mod API can be found on [Modrinth](https://modrinth.com/mod/hypixel-mod-api).
To install the mod, simply download the JAR file and place it in your mods folder. 

Currently, the Hypixel Mod API supports the following mod loaders and versions:

- [Fabric Latest](https://github.com/HypixelDev/FabricModAPI)
- [Forge 1.8.9](https://github.com/HypixelDev/ForgeModAPI)

If there is significant demand, support for additional versions and loaders may be considered.


## Developer Usage

For using the Mod API you will need to add it as a dependency to your project. This can be done via the public 
Hypixel Maven repository.

```xml
<repository>
    <id>Hypixel</id>
    <url>https://repo.hypixel.net/repository/Hypixel/</url>
</repository>
```

This repo can also be used with Gradle.

```gradle
repositories {
    maven { url 'https://repo.hypixel.net/repository/Hypixel/' }
}
```

You can then include the dependency in your project.

```xml
<dependency>
    <groupId>net.hypixel</groupId>
    <artifactId>mod-api</artifactId>
    <version>0.4.0</version>
</dependency>
```

```gradle
dependencies {
    implementation 'net.hypixel:mod-api:0.4.0'
}
```

Depending on your chosen mod loader, you will need to also include the `hypixel-mod-api` as a required dependency. For example in Fabric you would include the following in your `fabric.mod.json` file.

```json
{
  "depends": {
    "hypixel-mod-api": ">=0.4.0"
  }
}
```

## Example Usage

Once you have the API added to your project you can start using it. Below are examples of sending server-bound packets, as well as receiving client-bound packets.

### Sending a Hypixel Packet

```java
public class Example {
    public void sendPacket() {
        HypixelModAPI.getInstance().sendPacket(new ServerboundLocationPacket());
    }
}
```

### Registering a packet handler

```java

public class Example {
    public void registerPacketHandler() {
        HypixelModAPI.getInstance().registerHandler(new ClientboundPacketHandler() {
            @Override
            public void onLocationPacket(ClientboundLocationPacket packet) {
                packet.getServerName();
            }
        });
    }
}
```

### Subscribing to a packet event

If you wish to receive a specific event packet, you will need to subscribe to the event. Once subscribed you can register a packet handler as normal (see example above).

```java
public class Example {
    public void subscribeToPacketEvent() {
        HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket.class);
    }
}
```